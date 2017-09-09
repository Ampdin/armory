package com.spirovanni.blackshields.repository;

import com.spirovanni.blackshields.domain.ShoppingCart;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ShoppingCart entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {

}
