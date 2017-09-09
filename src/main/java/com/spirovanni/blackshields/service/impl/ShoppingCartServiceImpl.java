package com.spirovanni.blackshields.service.impl;

import com.spirovanni.blackshields.service.ShoppingCartService;
import com.spirovanni.blackshields.domain.ShoppingCart;
import com.spirovanni.blackshields.repository.ShoppingCartRepository;
import com.spirovanni.blackshields.repository.search.ShoppingCartSearchRepository;
import com.spirovanni.blackshields.service.dto.ShoppingCartDTO;
import com.spirovanni.blackshields.service.mapper.ShoppingCartMapper;
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
 * Service Implementation for managing ShoppingCart.
 */
@Service
@Transactional
public class ShoppingCartServiceImpl implements ShoppingCartService{

    private final Logger log = LoggerFactory.getLogger(ShoppingCartServiceImpl.class);

    private final ShoppingCartRepository shoppingCartRepository;

    private final ShoppingCartMapper shoppingCartMapper;

    private final ShoppingCartSearchRepository shoppingCartSearchRepository;
    public ShoppingCartServiceImpl(ShoppingCartRepository shoppingCartRepository, ShoppingCartMapper shoppingCartMapper, ShoppingCartSearchRepository shoppingCartSearchRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.shoppingCartMapper = shoppingCartMapper;
        this.shoppingCartSearchRepository = shoppingCartSearchRepository;
    }

    /**
     * Save a shoppingCart.
     *
     * @param shoppingCartDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ShoppingCartDTO save(ShoppingCartDTO shoppingCartDTO) {
        log.debug("Request to save ShoppingCart : {}", shoppingCartDTO);
        ShoppingCart shoppingCart = shoppingCartMapper.toEntity(shoppingCartDTO);
        shoppingCart = shoppingCartRepository.save(shoppingCart);
        ShoppingCartDTO result = shoppingCartMapper.toDto(shoppingCart);
        shoppingCartSearchRepository.save(shoppingCart);
        return result;
    }

    /**
     *  Get all the shoppingCarts.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ShoppingCartDTO> findAll() {
        log.debug("Request to get all ShoppingCarts");
        return shoppingCartRepository.findAll().stream()
            .map(shoppingCartMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one shoppingCart by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ShoppingCartDTO findOne(Long id) {
        log.debug("Request to get ShoppingCart : {}", id);
        ShoppingCart shoppingCart = shoppingCartRepository.findOne(id);
        return shoppingCartMapper.toDto(shoppingCart);
    }

    /**
     *  Delete the  shoppingCart by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ShoppingCart : {}", id);
        shoppingCartRepository.delete(id);
        shoppingCartSearchRepository.delete(id);
    }

    /**
     * Search for the shoppingCart corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ShoppingCartDTO> search(String query) {
        log.debug("Request to search ShoppingCarts for query {}", query);
        return StreamSupport
            .stream(shoppingCartSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(shoppingCartMapper::toDto)
            .collect(Collectors.toList());
    }
}
