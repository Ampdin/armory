package com.spirovanni.blackshields.service;

import com.spirovanni.blackshields.service.dto.CartItemDTO;
import java.util.List;

/**
 * Service Interface for managing CartItem.
 */
public interface CartItemService {

    /**
     * Save a cartItem.
     *
     * @param cartItemDTO the entity to save
     * @return the persisted entity
     */
    CartItemDTO save(CartItemDTO cartItemDTO);

    /**
     *  Get all the cartItems.
     *
     *  @return the list of entities
     */
    List<CartItemDTO> findAll();

    /**
     *  Get the "id" cartItem.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    CartItemDTO findOne(Long id);

    /**
     *  Delete the "id" cartItem.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the cartItem corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<CartItemDTO> search(String query);
}
