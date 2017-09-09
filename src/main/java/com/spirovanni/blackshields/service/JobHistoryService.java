package com.spirovanni.blackshields.service;

import com.spirovanni.blackshields.service.dto.JobHistoryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing JobHistory.
 */
public interface JobHistoryService {

    /**
     * Save a jobHistory.
     *
     * @param jobHistoryDTO the entity to save
     * @return the persisted entity
     */
    JobHistoryDTO save(JobHistoryDTO jobHistoryDTO);

    /**
     *  Get all the jobHistories.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<JobHistoryDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" jobHistory.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    JobHistoryDTO findOne(Long id);

    /**
     *  Delete the "id" jobHistory.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the jobHistory corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<JobHistoryDTO> search(String query, Pageable pageable);
}
