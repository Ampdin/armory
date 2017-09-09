package com.spirovanni.blackshields.service;

import com.spirovanni.blackshields.service.dto.ShoppingCartDTO;
import java.util.List;

/**
 * Service Interface for managing ShoppingCart.
 */
public interface ShoppingCartService {

    /**
     * Save a shoppingCart.
     *
     * @param shoppingCartDTO the entity to save
     * @return the persisted entity
     */
    ShoppingCartDTO save(ShoppingCartDTO shoppingCartDTO);

    /**
     *  Get all the shoppingCarts.
     *
     *  @return the list of entities
     */
    List<ShoppingCartDTO> findAll();

    /**
     *  Get the "id" shoppingCart.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ShoppingCartDTO findOne(Long id);

    /**
     *  Delete the "id" shoppingCart.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the shoppingCart corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<ShoppingCartDTO> search(String query);
}
