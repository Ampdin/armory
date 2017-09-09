package com.spirovanni.blackshields.service.mapper;

import com.spirovanni.blackshields.domain.*;
import com.spirovanni.blackshields.service.dto.ShoppingCartDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ShoppingCart and its DTO ShoppingCartDTO.
 */
@Mapper(componentModel = "spring", uses = {EmployeeMapper.class, CartItemMapper.class, })
public interface ShoppingCartMapper extends EntityMapper <ShoppingCartDTO, ShoppingCart> {

    @Mapping(source = "cart.id", target = "cartId")

    @Mapping(source = "cartItem.id", target = "cartItemId")
    ShoppingCartDTO toDto(ShoppingCart shoppingCart); 

    @Mapping(source = "cartId", target = "cart")

    @Mapping(source = "cartItemId", target = "cartItem")
    ShoppingCart toEntity(ShoppingCartDTO shoppingCartDTO); 
    default ShoppingCart fromId(Long id) {
        if (id == null) {
            return null;
        }
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(id);
        return shoppingCart;
    }
}
