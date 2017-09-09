package com.spirovanni.blackshields.web.rest;

import com.spirovanni.blackshields.ArmoryApp;

import com.spirovanni.blackshields.domain.JobHistory;
import com.spirovanni.blackshields.repository.JobHistoryRepository;
import com.spirovanni.blackshields.service.JobHistoryService;
import com.spirovanni.blackshields.repository.search.JobHistorySearchRepository;
import com.spirovanni.blackshields.service.dto.JobHistoryDTO;
import com.spirovanni.blackshields.service.mapper.JobHistoryMapper;
import com.spirovanni.blackshields.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.spirovanni.blackshields.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.spirovanni.blackshields.domain.enumeration.Language;
/**
 * Test class for the JobHistoryResource REST controller.
 *
 * @see JobHistoryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ArmoryApp.class)
public class JobHistoryResourceIntTest {

    private static final Long DEFAULT_JOB_HISTORY_ID = 1L;
    private static final Long UPDATED_JOB_HISTORY_ID = 2L;

    private static final ZonedDateTime DEFAULT_START_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_START_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_END_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_END_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Language DEFAULT_LANGUAGE = Language.FRENCH;
    private static final Language UPDATED_LANGUAGE = Language.ENGLISH;

    @Autowired
    private JobHistoryRepository jobHistoryRepository;

    @Autowired
    private JobHistoryMapper jobHistoryMapper;

    @Autowired
    private JobHistoryService jobHistoryService;

    @Autowired
    private JobHistorySearchRepository jobHistorySearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restJobHistoryMockMvc;

    private JobHistory jobHistory;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final JobHistoryResource jobHistoryResource = new JobHistoryResource(jobHistoryService);
        this.restJobHistoryMockMvc = MockMvcBuilders.standaloneSetup(jobHistoryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JobHistory createEntity(EntityManager em) {
        JobHistory jobHistory = new JobHistory()
            .jobHistoryId(DEFAULT_JOB_HISTORY_ID)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .language(DEFAULT_LANGUAGE);
        return jobHistory;
    }

    @Before
    public void initTest() {
        jobHistorySearchRepository.deleteAll();
        jobHistory = createEntity(em);
    }

    @Test
    @Transactional
    public void createJobHistory() throws Exception {
        int databaseSizeBeforeCreate = jobHistoryRepository.findAll().size();

        // Create the JobHistory
        JobHistoryDTO jobHistoryDTO = jobHistoryMapper.toDto(jobHistory);
        restJobHistoryMockMvc.perform(post("/api/job-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jobHistoryDTO)))
            .andExpect(status().isCreated());

        // Validate the JobHistory in the database
        List<JobHistory> jobHistoryList = jobHistoryRepository.findAll();
        assertThat(jobHistoryList).hasSize(databaseSizeBeforeCreate + 1);
        JobHistory testJobHistory = jobHistoryList.get(jobHistoryList.size() - 1);
        assertThat(testJobHistory.getJobHistoryId()).isEqualTo(DEFAULT_JOB_HISTORY_ID);
        assertThat(testJobHistory.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testJobHistory.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testJobHistory.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);

        // Validate the JobHistory in Elasticsearch
        JobHistory jobHistoryEs = jobHistorySearchRepository.findOne(testJobHistory.getId());
        assertThat(jobHistoryEs).isEqualToComparingFieldByField(testJobHistory);
    }

    @Test
    @Transactional
    public void createJobHistoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = jobHistoryRepository.findAll().size();

        // Create the JobHistory with an existing ID
        jobHistory.setId(1L);
        JobHistoryDTO jobHistoryDTO = jobHistoryMapper.toDto(jobHistory);

        // An entity with an existing ID cannot be created, so this API call must fail
        restJobHistoryMockMvc.perform(post("/api/job-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jobHistoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<JobHistory> jobHistoryList = jobHistoryRepository.findAll();
        assertThat(jobHistoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllJobHistories() throws Exception {
        // Initialize the database
        jobHistoryRepository.saveAndFlush(jobHistory);

        // Get all the jobHistoryList
        restJobHistoryMockMvc.perform(get("/api/job-histories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jobHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].jobHistoryId").value(hasItem(DEFAULT_JOB_HISTORY_ID.intValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(sameInstant(DEFAULT_START_DATE))))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(sameInstant(DEFAULT_END_DATE))))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE.toString())));
    }

    @Test
    @Transactional
    public void getJobHistory() throws Exception {
        // Initialize the database
        jobHistoryRepository.saveAndFlush(jobHistory);

        // Get the jobHistory
        restJobHistoryMockMvc.perform(get("/api/job-histories/{id}", jobHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(jobHistory.getId().intValue()))
            .andExpect(jsonPath("$.jobHistoryId").value(DEFAULT_JOB_HISTORY_ID.intValue()))
            .andExpect(jsonPath("$.startDate").value(sameInstant(DEFAULT_START_DATE)))
            .andExpect(jsonPath("$.endDate").value(sameInstant(DEFAULT_END_DATE)))
            .andExpect(jsonPath("$.language").value(DEFAULT_LANGUAGE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingJobHistory() throws Exception {
        // Get the jobHistory
        restJobHistoryMockMvc.perform(get("/api/job-histories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateJobHistory() throws Exception {
        // Initialize the database
        jobHistoryRepository.saveAndFlush(jobHistory);
        jobHistorySearchRepository.save(jobHistory);
        int databaseSizeBeforeUpdate = jobHistoryRepository.findAll().size();

        // Update the jobHistory
        JobHistory updatedJobHistory = jobHistoryRepository.findOne(jobHistory.getId());
        updatedJobHistory
            .jobHistoryId(UPDATED_JOB_HISTORY_ID)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .language(UPDATED_LANGUAGE);
        JobHistoryDTO jobHistoryDTO = jobHistoryMapper.toDto(updatedJobHistory);

        restJobHistoryMockMvc.perform(put("/api/job-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jobHistoryDTO)))
            .andExpect(status().isOk());

        // Validate the JobHistory in the database
        List<JobHistory> jobHistoryList = jobHistoryRepository.findAll();
        assertThat(jobHistoryList).hasSize(databaseSizeBeforeUpdate);
        JobHistory testJobHistory = jobHistoryList.get(jobHistoryList.size() - 1);
        assertThat(testJobHistory.getJobHistoryId()).isEqualTo(UPDATED_JOB_HISTORY_ID);
        assertThat(testJobHistory.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testJobHistory.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testJobHistory.getLanguage()).isEqualTo(UPDATED_LANGUAGE);

        // Validate the JobHistory in Elasticsearch
        JobHistory jobHistoryEs = jobHistorySearchRepository.findOne(testJobHistory.getId());
        assertThat(jobHistoryEs).isEqualToComparingFieldByField(testJobHistory);
    }

    @Test
    @Transactional
    public void updateNonExistingJobHistory() throws Exception {
        int databaseSizeBeforeUpdate = jobHistoryRepository.findAll().size();

        // Create the JobHistory
        JobHistoryDTO jobHistoryDTO = jobHistoryMapper.toDto(jobHistory);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restJobHistoryMockMvc.perform(put("/api/job-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jobHistoryDTO)))
            .andExpect(status().isCreated());

        // Validate the JobHistory in the database
        List<JobHistory> jobHistoryList = jobHistoryRepository.findAll();
        assertThat(jobHistoryList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteJobHistory() throws Exception {
        // Initialize the database
        jobHistoryRepository.saveAndFlush(jobHistory);
        jobHistorySearchRepository.save(jobHistory);
        int databaseSizeBeforeDelete = jobHistoryRepository.findAll().size();

        // Get the jobHistory
        restJobHistoryMockMvc.perform(delete("/api/job-histories/{id}", jobHistory.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean jobHistoryExistsInEs = jobHistorySearchRepository.exists(jobHistory.getId());
        assertThat(jobHistoryExistsInEs).isFalse();

        // Validate the database is empty
        List<JobHistory> jobHistoryList = jobHistoryRepository.findAll();
        assertThat(jobHistoryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchJobHistory() throws Exception {
        // Initialize the database
        jobHistoryRepository.saveAndFlush(jobHistory);
        jobHistorySearchRepository.save(jobHistory);

        // Search the jobHistory
        restJobHistoryMockMvc.perform(get("/api/_search/job-histories?query=id:" + jobHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jobHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].jobHistoryId").value(hasItem(DEFAULT_JOB_HISTORY_ID.intValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(sameInstant(DEFAULT_START_DATE))))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(sameInstant(DEFAULT_END_DATE))))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(JobHistory.class);
        JobHistory jobHistory1 = new JobHistory();
        jobHistory1.setId(1L);
        JobHistory jobHistory2 = new JobHistory();
        jobHistory2.setId(jobHistory1.getId());
        assertThat(jobHistory1).isEqualTo(jobHistory2);
        jobHistory2.setId(2L);
        assertThat(jobHistory1).isNotEqualTo(jobHistory2);
        jobHistory1.setId(null);
        assertThat(jobHistory1).isNotEqualTo(jobHistory2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(JobHistoryDTO.class);
        JobHistoryDTO jobHistoryDTO1 = new JobHistoryDTO();
        jobHistoryDTO1.setId(1L);
        JobHistoryDTO jobHistoryDTO2 = new JobHistoryDTO();
        assertThat(jobHistoryDTO1).isNotEqualTo(jobHistoryDTO2);
        jobHistoryDTO2.setId(jobHistoryDTO1.getId());
        assertThat(jobHistoryDTO1).isEqualTo(jobHistoryDTO2);
        jobHistoryDTO2.setId(2L);
        assertThat(jobHistoryDTO1).isNotEqualTo(jobHistoryDTO2);
        jobHistoryDTO1.setId(null);
        assertThat(jobHistoryDTO1).isNotEqualTo(jobHistoryDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(jobHistoryMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(jobHistoryMapper.fromId(null)).isNull();
    }
}
