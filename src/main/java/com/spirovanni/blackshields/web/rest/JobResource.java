package com.spirovanni.blackshields.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.spirovanni.blackshields.domain.Job;

import com.spirovanni.blackshields.repository.JobRepository;
import com.spirovanni.blackshields.repository.search.JobSearchRepository;
import com.spirovanni.blackshields.web.rest.util.HeaderUtil;
import com.spirovanni.blackshields.web.rest.util.PaginationUtil;
import com.spirovanni.blackshields.service.dto.JobDTO;
import com.spirovanni.blackshields.service.mapper.JobMapper;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Job.
 */
@RestController
@RequestMapping("/api")
public class JobResource {

    private final Logger log = LoggerFactory.getLogger(JobResource.class);

    private static final String ENTITY_NAME = "job";

    private final JobRepository jobRepository;

    private final JobMapper jobMapper;

    private final JobSearchRepository jobSearchRepository;
    public JobResource(JobRepository jobRepository, JobMapper jobMapper, JobSearchRepository jobSearchRepository) {
        this.jobRepository = jobRepository;
        this.jobMapper = jobMapper;
        this.jobSearchRepository = jobSearchRepository;
    }

    /**
     * POST  /jobs : Create a new job.
     *
     * @param jobDTO the jobDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new jobDTO, or with status 400 (Bad Request) if the job has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/jobs")
    @Timed
    public ResponseEntity<JobDTO> createJob(@RequestBody JobDTO jobDTO) throws URISyntaxException {
        log.debug("REST request to save Job : {}", jobDTO);
        if (jobDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new job cannot already have an ID")).body(null);
        }
        Job job = jobMapper.toEntity(jobDTO);
        job = jobRepository.save(job);
        JobDTO result = jobMapper.toDto(job);
        jobSearchRepository.save(job);
        return ResponseEntity.created(new URI("/api/jobs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /jobs : Updates an existing job.
     *
     * @param jobDTO the jobDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated jobDTO,
     * or with status 400 (Bad Request) if the jobDTO is not valid,
     * or with status 500 (Internal Server Error) if the jobDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/jobs")
    @Timed
    public ResponseEntity<JobDTO> updateJob(@RequestBody JobDTO jobDTO) throws URISyntaxException {
        log.debug("REST request to update Job : {}", jobDTO);
        if (jobDTO.getId() == null) {
            return createJob(jobDTO);
        }
        Job job = jobMapper.toEntity(jobDTO);
        job = jobRepository.save(job);
        JobDTO result = jobMapper.toDto(job);
        jobSearchRepository.save(job);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, jobDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /jobs : get all the jobs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of jobs in body
     */
    @GetMapping("/jobs")
    @Timed
    public ResponseEntity<List<JobDTO>> getAllJobs(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Jobs");
        Page<Job> page = jobRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/jobs");
        return new ResponseEntity<>(jobMapper.toDto(page.getContent()), headers, HttpStatus.OK);
    }

    /**
     * GET  /jobs/:id : get the "id" job.
     *
     * @param id the id of the jobDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the jobDTO, or with status 404 (Not Found)
     */
    @GetMapping("/jobs/{id}")
    @Timed
    public ResponseEntity<JobDTO> getJob(@PathVariable Long id) {
        log.debug("REST request to get Job : {}", id);
        Job job = jobRepository.findOneWithEagerRelationships(id);
        JobDTO jobDTO = jobMapper.toDto(job);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(jobDTO));
    }

    /**
     * DELETE  /jobs/:id : delete the "id" job.
     *
     * @param id the id of the jobDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/jobs/{id}")
    @Timed
    public ResponseEntity<Void> deleteJob(@PathVariable Long id) {
        log.debug("REST request to delete Job : {}", id);
        jobRepository.delete(id);
        jobSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/jobs?query=:query : search for the job corresponding
     * to the query.
     *
     * @param query the query of the job search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/jobs")
    @Timed
    public ResponseEntity<List<JobDTO>> searchJobs(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of Jobs for query {}", query);
        Page<Job> page = jobSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/jobs");
        return new ResponseEntity<>(jobMapper.toDto(page.getContent()), headers, HttpStatus.OK);
    }

}
