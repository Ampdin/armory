package com.spirovanni.blackshields.service.impl;

import com.spirovanni.blackshields.service.CartItemService;
import com.spirovanni.blackshields.domain.CartItem;
import com.spirovanni.blackshields.repository.CartItemRepository;
import com.spirovanni.blackshields.repository.search.CartItemSearchRepository;
import com.spirovanni.blackshields.service.dto.CartItemDTO;
import com.spirovanni.blackshields.service.mapper.CartItemMapper;
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
 * Service Implementation for managing CartItem.
 */
@Service
@Transactional
public class CartItemServiceImpl implements CartItemService{

    private final Logger log = LoggerFactory.getLogger(CartItemServiceImpl.class);

    private final CartItemRepository cartItemRepository;

    private final CartItemMapper cartItemMapper;

    private final CartItemSearchRepository cartItemSearchRepository;
    public CartItemServiceImpl(CartItemRepository cartItemRepository, CartItemMapper cartItemMapper, CartItemSearchRepository cartItemSearchRepository) {
        this.cartItemRepository = cartItemRepository;
        this.cartItemMapper = cartItemMapper;
        this.cartItemSearchRepository = cartItemSearchRepository;
    }

    /**
     * Save a cartItem.
     *
     * @param cartItemDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CartItemDTO save(CartItemDTO cartItemDTO) {
        log.debug("Request to save CartItem : {}", cartItemDTO);
        CartItem cartItem = cartItemMapper.toEntity(cartItemDTO);
        cartItem = cartItemRepository.save(cartItem);
        CartItemDTO result = cartItemMapper.toDto(cartItem);
        cartItemSearchRepository.save(cartItem);
        return result;
    }

    /**
     *  Get all the cartItems.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<CartItemDTO> findAll() {
        log.debug("Request to get all CartItems");
        return cartItemRepository.findAll().stream()
            .map(cartItemMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one cartItem by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public CartItemDTO findOne(Long id) {
        log.debug("Request to get CartItem : {}", id);
        CartItem cartItem = cartItemRepository.findOne(id);
        return cartItemMapper.toDto(cartItem);
    }

    /**
     *  Delete the  cartItem by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CartItem : {}", id);
        cartItemRepository.delete(id);
        cartItemSearchRepository.delete(id);
    }

    /**
     * Search for the cartItem corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<CartItemDTO> search(String query) {
        log.debug("Request to search CartItems for query {}", query);
        return StreamSupport
            .stream(cartItemSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(cartItemMapper::toDto)
            .collect(Collectors.toList());
    }
}
