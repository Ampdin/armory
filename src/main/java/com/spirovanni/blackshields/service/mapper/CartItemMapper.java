package com.spirovanni.blackshields.service.mapper;

import com.spirovanni.blackshields.domain.*;
import com.spirovanni.blackshields.service.dto.CartItemDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CartItem and its DTO CartItemDTO.
 */
@Mapper(componentModel = "spring", uses = {BookMapper.class, })
public interface CartItemMapper extends EntityMapper <CartItemDTO, CartItem> {

    @Mapping(source = "book.id", target = "bookId")
    CartItemDTO toDto(CartItem cartItem); 
    @Mapping(target = "shoppingCarts", ignore = true)

    @Mapping(source = "bookId", target = "book")
    CartItem toEntity(CartItemDTO cartItemDTO); 
    default CartItem fromId(Long id) {
        if (id == null) {
            return null;
        }
        CartItem cartItem = new CartItem();
        cartItem.setId(id);
        return cartItem;
    }
}
