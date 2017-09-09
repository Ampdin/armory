package com.spirovanni.blackshields.service.impl;

import com.spirovanni.blackshields.service.TaskService;
import com.spirovanni.blackshields.domain.Task;
import com.spirovanni.blackshields.repository.TaskRepository;
import com.spirovanni.blackshields.repository.search.TaskSearchRepository;
import com.spirovanni.blackshields.service.dto.TaskDTO;
import com.spirovanni.blackshields.service.mapper.TaskMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Task.
 */
@Service
@Transactional
public class TaskServiceImpl implements TaskService{

    private final Logger log = LoggerFactory.getLogger(TaskServiceImpl.class);

    private final TaskRepository taskRepository;

    private final TaskMapper taskMapper;

    private final TaskSearchRepository taskSearchRepository;
    public TaskServiceImpl(TaskRepository taskRepository, TaskMapper taskMapper, TaskSearchRepository taskSearchRepository) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
        this.taskSearchRepository = taskSearchRepository;
    }

    /**
     * Save a task.
     *
     * @param taskDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public TaskDTO save(TaskDTO taskDTO) {
        log.debug("Request to save Task : {}", taskDTO);
        Task task = taskMapper.toEntity(taskDTO);
        task = taskRepository.save(task);
        TaskDTO result = taskMapper.toDto(task);
        taskSearchRepository.save(task);
        return result;
    }

    /**
     *  Get all the tasks.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<TaskDTO> findAll() {
        log.debug("Request to get all Tasks");
        return taskRepository.findAll().stream()
            .map(taskMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one task by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public TaskDTO findOne(Long id) {
        log.debug("Request to get Task : {}", id);
        Task task = taskRepository.findOne(id);
        return taskMapper.toDto(task);
    }

    /**
     *  Delete the  task by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Task : {}", id);
        taskRepository.delete(id);
        taskSearchRepository.delete(id);
    }

    /**
     * Search for the task corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<TaskDTO> search(String query) {
        log.debug("Request to search Tasks for query {}", query);
        return StreamSupport
            .stream(taskSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(taskMapper::toDto)
            .collect(Collectors.toList());
    }
}
