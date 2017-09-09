package com.spirovanni.blackshields.service;

import com.spirovanni.blackshields.service.dto.DepartmentDTO;
import java.util.List;

/**
 * Service Interface for managing Department.
 */
public interface DepartmentService {

    /**
     * Save a department.
     *
     * @param departmentDTO the entity to save
     * @return the persisted entity
     */
    DepartmentDTO save(DepartmentDTO departmentDTO);

    /**
     *  Get all the departments.
     *
     *  @return the list of entities
     */
    List<DepartmentDTO> findAll();

    /**
     *  Get the "id" department.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    DepartmentDTO findOne(Long id);

    /**
     *  Delete the "id" department.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the department corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<DepartmentDTO> search(String query);
}
