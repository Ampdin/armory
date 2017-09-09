package com.spirovanni.blackshields.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the ShoppingCart entity.
 */
public class ShoppingCartDTO implements Serializable {

    private Long id;

    private Long shoppingCartId;

    private String cartStatus;

    private String saveForLater;

    private Long cartId;

    private Long cartItemId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getShoppingCartId() {
        return shoppingCartId;
    }

    public void setShoppingCartId(Long shoppingCartId) {
        this.shoppingCartId = shoppingCartId;
    }

    public String getCartStatus() {
        return cartStatus;
    }

    public void setCartStatus(String cartStatus) {
        this.cartStatus = cartStatus;
    }

    public String getSaveForLater() {
        return saveForLater;
    }

    public void setSaveForLater(String saveForLater) {
        this.saveForLater = saveForLater;
    }

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long employeeId) {
        this.cartId = employeeId;
    }

    public Long getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(Long cartItemId) {
        this.cartItemId = cartItemId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ShoppingCartDTO shoppingCartDTO = (ShoppingCartDTO) o;
        if(shoppingCartDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), shoppingCartDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ShoppingCartDTO{" +
            "id=" + getId() +
            ", shoppingCartId='" + getShoppingCartId() + "'" +
            ", cartStatus='" + getCartStatus() + "'" +
            ", saveForLater='" + getSaveForLater() + "'" +
            "}";
    }
}
