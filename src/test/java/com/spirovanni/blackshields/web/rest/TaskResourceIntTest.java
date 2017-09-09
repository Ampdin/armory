package com.spirovanni.blackshields.web.rest;

import com.spirovanni.blackshields.ArmoryApp;

import com.spirovanni.blackshields.domain.Task;
import com.spirovanni.blackshields.repository.TaskRepository;
import com.spirovanni.blackshields.service.TaskService;
import com.spirovanni.blackshields.repository.search.TaskSearchRepository;
import com.spirovanni.blackshields.service.dto.TaskDTO;
import com.spirovanni.blackshields.service.mapper.TaskMapper;
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
import java.math.BigDecimal;
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

/**
 * Test class for the TaskResource REST controller.
 *
 * @see TaskResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ArmoryApp.class)
public class TaskResourceIntTest {

    private static final String DEFAULT_TASK_ID = "AAAAAAAAAA";
    private static final String UPDATED_TASK_ID = "BBBBBBBBBB";

    private static final String DEFAULT_TASK_NAME = "AAAAAAAAAA";
    private static final String UPDATED_TASK_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SCALE_ID = "AAAAAAAAAA";
    private static final String UPDATED_SCALE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_SCALE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SCALE_NAME = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_DATA_VALUE = new BigDecimal(1);
    private static final BigDecimal UPDATED_DATA_VALUE = new BigDecimal(2);

    private static final Long DEFAULT_N = 1L;
    private static final Long UPDATED_N = 2L;

    private static final BigDecimal DEFAULT_STANDARD_ERROR = new BigDecimal(1);
    private static final BigDecimal UPDATED_STANDARD_ERROR = new BigDecimal(2);

    private static final BigDecimal DEFAULT_LOWER_CL_BOUND = new BigDecimal(1);
    private static final BigDecimal UPDATED_LOWER_CL_BOUND = new BigDecimal(2);

    private static final BigDecimal DEFAULT_UPPER_CL_BOUND = new BigDecimal(1);
    private static final BigDecimal UPDATED_UPPER_CL_BOUND = new BigDecimal(2);

    private static final String DEFAULT_RECOMMEND_SUPPRESS = "AAAAAAAAAA";
    private static final String UPDATED_RECOMMEND_SUPPRESS = "BBBBBBBBBB";

    private static final String DEFAULT_NOT_RELEVANT = "AAAAAAAAAA";
    private static final String UPDATED_NOT_RELEVANT = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_DOMAIN_SOURCE = "AAAAAAAAAA";
    private static final String UPDATED_DOMAIN_SOURCE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private TaskService taskService;

    @Autowired
    private TaskSearchRepository taskSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTaskMockMvc;

    private Task task;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TaskResource taskResource = new TaskResource(taskService);
        this.restTaskMockMvc = MockMvcBuilders.standaloneSetup(taskResource)
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
    public static Task createEntity(EntityManager em) {
        Task task = new Task()
            .taskID(DEFAULT_TASK_ID)
            .taskName(DEFAULT_TASK_NAME)
            .scaleID(DEFAULT_SCALE_ID)
            .scaleName(DEFAULT_SCALE_NAME)
            .dataValue(DEFAULT_DATA_VALUE)
            .n(DEFAULT_N)
            .standardError(DEFAULT_STANDARD_ERROR)
            .lowerClBound(DEFAULT_LOWER_CL_BOUND)
            .upperClBound(DEFAULT_UPPER_CL_BOUND)
            .recommendSuppress(DEFAULT_RECOMMEND_SUPPRESS)
            .notRelevant(DEFAULT_NOT_RELEVANT)
            .date(DEFAULT_DATE)
            .domainSource(DEFAULT_DOMAIN_SOURCE)
            .description(DEFAULT_DESCRIPTION);
        return task;
    }

    @Before
    public void initTest() {
        taskSearchRepository.deleteAll();
        task = createEntity(em);
    }

    @Test
    @Transactional
    public void createTask() throws Exception {
        int databaseSizeBeforeCreate = taskRepository.findAll().size();

        // Create the Task
        TaskDTO taskDTO = taskMapper.toDto(task);
        restTaskMockMvc.perform(post("/api/tasks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(taskDTO)))
            .andExpect(status().isCreated());

        // Validate the Task in the database
        List<Task> taskList = taskRepository.findAll();
        assertThat(taskList).hasSize(databaseSizeBeforeCreate + 1);
        Task testTask = taskList.get(taskList.size() - 1);
        assertThat(testTask.getTaskID()).isEqualTo(DEFAULT_TASK_ID);
        assertThat(testTask.getTaskName()).isEqualTo(DEFAULT_TASK_NAME);
        assertThat(testTask.getScaleID()).isEqualTo(DEFAULT_SCALE_ID);
        assertThat(testTask.getScaleName()).isEqualTo(DEFAULT_SCALE_NAME);
        assertThat(testTask.getDataValue()).isEqualTo(DEFAULT_DATA_VALUE);
        assertThat(testTask.getN()).isEqualTo(DEFAULT_N);
        assertThat(testTask.getStandardError()).isEqualTo(DEFAULT_STANDARD_ERROR);
        assertThat(testTask.getLowerClBound()).isEqualTo(DEFAULT_LOWER_CL_BOUND);
        assertThat(testTask.getUpperClBound()).isEqualTo(DEFAULT_UPPER_CL_BOUND);
        assertThat(testTask.getRecommendSuppress()).isEqualTo(DEFAULT_RECOMMEND_SUPPRESS);
        assertThat(testTask.getNotRelevant()).isEqualTo(DEFAULT_NOT_RELEVANT);
        assertThat(testTask.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testTask.getDomainSource()).isEqualTo(DEFAULT_DOMAIN_SOURCE);
        assertThat(testTask.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);

        // Validate the Task in Elasticsearch
        Task taskEs = taskSearchRepository.findOne(testTask.getId());
        assertThat(taskEs).isEqualToComparingFieldByField(testTask);
    }

    @Test
    @Transactional
    public void createTaskWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = taskRepository.findAll().size();

        // Create the Task with an existing ID
        task.setId(1L);
        TaskDTO taskDTO = taskMapper.toDto(task);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTaskMockMvc.perform(post("/api/tasks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(taskDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Task> taskList = taskRepository.findAll();
        assertThat(taskList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTasks() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList
        restTaskMockMvc.perform(get("/api/tasks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(task.getId().intValue())))
            .andExpect(jsonPath("$.[*].taskID").value(hasItem(DEFAULT_TASK_ID.toString())))
            .andExpect(jsonPath("$.[*].taskName").value(hasItem(DEFAULT_TASK_NAME.toString())))
            .andExpect(jsonPath("$.[*].scaleID").value(hasItem(DEFAULT_SCALE_ID.toString())))
            .andExpect(jsonPath("$.[*].scaleName").value(hasItem(DEFAULT_SCALE_NAME.toString())))
            .andExpect(jsonPath("$.[*].dataValue").value(hasItem(DEFAULT_DATA_VALUE.intValue())))
            .andExpect(jsonPath("$.[*].n").value(hasItem(DEFAULT_N.intValue())))
            .andExpect(jsonPath("$.[*].standardError").value(hasItem(DEFAULT_STANDARD_ERROR.intValue())))
            .andExpect(jsonPath("$.[*].lowerClBound").value(hasItem(DEFAULT_LOWER_CL_BOUND.intValue())))
            .andExpect(jsonPath("$.[*].upperClBound").value(hasItem(DEFAULT_UPPER_CL_BOUND.intValue())))
            .andExpect(jsonPath("$.[*].recommendSuppress").value(hasItem(DEFAULT_RECOMMEND_SUPPRESS.toString())))
            .andExpect(jsonPath("$.[*].notRelevant").value(hasItem(DEFAULT_NOT_RELEVANT.toString())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(sameInstant(DEFAULT_DATE))))
            .andExpect(jsonPath("$.[*].domainSource").value(hasItem(DEFAULT_DOMAIN_SOURCE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getTask() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get the task
        restTaskMockMvc.perform(get("/api/tasks/{id}", task.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(task.getId().intValue()))
            .andExpect(jsonPath("$.taskID").value(DEFAULT_TASK_ID.toString()))
            .andExpect(jsonPath("$.taskName").value(DEFAULT_TASK_NAME.toString()))
            .andExpect(jsonPath("$.scaleID").value(DEFAULT_SCALE_ID.toString()))
            .andExpect(jsonPath("$.scaleName").value(DEFAULT_SCALE_NAME.toString()))
            .andExpect(jsonPath("$.dataValue").value(DEFAULT_DATA_VALUE.intValue()))
            .andExpect(jsonPath("$.n").value(DEFAULT_N.intValue()))
            .andExpect(jsonPath("$.standardError").value(DEFAULT_STANDARD_ERROR.intValue()))
            .andExpect(jsonPath("$.lowerClBound").value(DEFAULT_LOWER_CL_BOUND.intValue()))
            .andExpect(jsonPath("$.upperClBound").value(DEFAULT_UPPER_CL_BOUND.intValue()))
            .andExpect(jsonPath("$.recommendSuppress").value(DEFAULT_RECOMMEND_SUPPRESS.toString()))
            .andExpect(jsonPath("$.notRelevant").value(DEFAULT_NOT_RELEVANT.toString()))
            .andExpect(jsonPath("$.date").value(sameInstant(DEFAULT_DATE)))
            .andExpect(jsonPath("$.domainSource").value(DEFAULT_DOMAIN_SOURCE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTask() throws Exception {
        // Get the task
        restTaskMockMvc.perform(get("/api/tasks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTask() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);
        taskSearchRepository.save(task);
        int databaseSizeBeforeUpdate = taskRepository.findAll().size();

        // Update the task
        Task updatedTask = taskRepository.findOne(task.getId());
        updatedTask
            .taskID(UPDATED_TASK_ID)
            .taskName(UPDATED_TASK_NAME)
            .scaleID(UPDATED_SCALE_ID)
            .scaleName(UPDATED_SCALE_NAME)
            .dataValue(UPDATED_DATA_VALUE)
            .n(UPDATED_N)
            .standardError(UPDATED_STANDARD_ERROR)
            .lowerClBound(UPDATED_LOWER_CL_BOUND)
            .upperClBound(UPDATED_UPPER_CL_BOUND)
            .recommendSuppress(UPDATED_RECOMMEND_SUPPRESS)
            .notRelevant(UPDATED_NOT_RELEVANT)
            .date(UPDATED_DATE)
            .domainSource(UPDATED_DOMAIN_SOURCE)
            .description(UPDATED_DESCRIPTION);
        TaskDTO taskDTO = taskMapper.toDto(updatedTask);

        restTaskMockMvc.perform(put("/api/tasks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(taskDTO)))
            .andExpect(status().isOk());

        // Validate the Task in the database
        List<Task> taskList = taskRepository.findAll();
        assertThat(taskList).hasSize(databaseSizeBeforeUpdate);
        Task testTask = taskList.get(taskList.size() - 1);
        assertThat(testTask.getTaskID()).isEqualTo(UPDATED_TASK_ID);
        assertThat(testTask.getTaskName()).isEqualTo(UPDATED_TASK_NAME);
        assertThat(testTask.getScaleID()).isEqualTo(UPDATED_SCALE_ID);
        assertThat(testTask.getScaleName()).isEqualTo(UPDATED_SCALE_NAME);
        assertThat(testTask.getDataValue()).isEqualTo(UPDATED_DATA_VALUE);
        assertThat(testTask.getN()).isEqualTo(UPDATED_N);
        assertThat(testTask.getStandardError()).isEqualTo(UPDATED_STANDARD_ERROR);
        assertThat(testTask.getLowerClBound()).isEqualTo(UPDATED_LOWER_CL_BOUND);
        assertThat(testTask.getUpperClBound()).isEqualTo(UPDATED_UPPER_CL_BOUND);
        assertThat(testTask.getRecommendSuppress()).isEqualTo(UPDATED_RECOMMEND_SUPPRESS);
        assertThat(testTask.getNotRelevant()).isEqualTo(UPDATED_NOT_RELEVANT);
        assertThat(testTask.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testTask.getDomainSource()).isEqualTo(UPDATED_DOMAIN_SOURCE);
        assertThat(testTask.getDescription()).isEqualTo(UPDATED_DESCRIPTION);

        // Validate the Task in Elasticsearch
        Task taskEs = taskSearchRepository.findOne(testTask.getId());
        assertThat(taskEs).isEqualToComparingFieldByField(testTask);
    }

    @Test
    @Transactional
    public void updateNonExistingTask() throws Exception {
        int databaseSizeBeforeUpdate = taskRepository.findAll().size();

        // Create the Task
        TaskDTO taskDTO = taskMapper.toDto(task);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTaskMockMvc.perform(put("/api/tasks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(taskDTO)))
            .andExpect(status().isCreated());

        // Validate the Task in the database
        List<Task> taskList = taskRepository.findAll();
        assertThat(taskList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTask() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);
        taskSearchRepository.save(task);
        int databaseSizeBeforeDelete = taskRepository.findAll().size();

        // Get the task
        restTaskMockMvc.perform(delete("/api/tasks/{id}", task.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean taskExistsInEs = taskSearchRepository.exists(task.getId());
        assertThat(taskExistsInEs).isFalse();

        // Validate the database is empty
        List<Task> taskList = taskRepository.findAll();
        assertThat(taskList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchTask() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);
        taskSearchRepository.save(task);

        // Search the task
        restTaskMockMvc.perform(get("/api/_search/tasks?query=id:" + task.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(task.getId().intValue())))
            .andExpect(jsonPath("$.[*].taskID").value(hasItem(DEFAULT_TASK_ID.toString())))
            .andExpect(jsonPath("$.[*].taskName").value(hasItem(DEFAULT_TASK_NAME.toString())))
            .andExpect(jsonPath("$.[*].scaleID").value(hasItem(DEFAULT_SCALE_ID.toString())))
            .andExpect(jsonPath("$.[*].scaleName").value(hasItem(DEFAULT_SCALE_NAME.toString())))
            .andExpect(jsonPath("$.[*].dataValue").value(hasItem(DEFAULT_DATA_VALUE.intValue())))
            .andExpect(jsonPath("$.[*].n").value(hasItem(DEFAULT_N.intValue())))
            .andExpect(jsonPath("$.[*].standardError").value(hasItem(DEFAULT_STANDARD_ERROR.intValue())))
            .andExpect(jsonPath("$.[*].lowerClBound").value(hasItem(DEFAULT_LOWER_CL_BOUND.intValue())))
            .andExpect(jsonPath("$.[*].upperClBound").value(hasItem(DEFAULT_UPPER_CL_BOUND.intValue())))
            .andExpect(jsonPath("$.[*].recommendSuppress").value(hasItem(DEFAULT_RECOMMEND_SUPPRESS.toString())))
            .andExpect(jsonPath("$.[*].notRelevant").value(hasItem(DEFAULT_NOT_RELEVANT.toString())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(sameInstant(DEFAULT_DATE))))
            .andExpect(jsonPath("$.[*].domainSource").value(hasItem(DEFAULT_DOMAIN_SOURCE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Task.class);
        Task task1 = new Task();
        task1.setId(1L);
        Task task2 = new Task();
        task2.setId(task1.getId());
        assertThat(task1).isEqualTo(task2);
        task2.setId(2L);
        assertThat(task1).isNotEqualTo(task2);
        task1.setId(null);
        assertThat(task1).isNotEqualTo(task2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TaskDTO.class);
        TaskDTO taskDTO1 = new TaskDTO();
        taskDTO1.setId(1L);
        TaskDTO taskDTO2 = new TaskDTO();
        assertThat(taskDTO1).isNotEqualTo(taskDTO2);
        taskDTO2.setId(taskDTO1.getId());
        assertThat(taskDTO1).isEqualTo(taskDTO2);
        taskDTO2.setId(2L);
        assertThat(taskDTO1).isNotEqualTo(taskDTO2);
        taskDTO1.setId(null);
        assertThat(taskDTO1).isNotEqualTo(taskDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(taskMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(taskMapper.fromId(null)).isNull();
    }
}
