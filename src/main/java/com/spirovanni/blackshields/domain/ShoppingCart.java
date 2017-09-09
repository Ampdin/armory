package com.spirovanni.blackshields.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ShoppingCart.
 */
@Entity
@Table(name = "shopping_cart")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "shoppingcart")
public class ShoppingCart implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "shopping_cart_id")
    private Long shoppingCartId;

    @Column(name = "cart_status")
    private String cartStatus;

    @Column(name = "save_for_later")
    private String saveForLater;

    @OneToOne
    @JoinColumn(unique = true)
    private Employee cart;

    @ManyToOne
    private CartItem cartItem;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getShoppingCartId() {
        return shoppingCartId;
    }

    public ShoppingCart shoppingCartId(Long shoppingCartId) {
        this.shoppingCartId = shoppingCartId;
        return this;
    }

    public void setShoppingCartId(Long shoppingCartId) {
        this.shoppingCartId = shoppingCartId;
    }

    public String getCartStatus() {
        return cartStatus;
    }

    public ShoppingCart cartStatus(String cartStatus) {
        this.cartStatus = cartStatus;
        return this;
    }

    public void setCartStatus(String cartStatus) {
        this.cartStatus = cartStatus;
    }

    public String getSaveForLater() {
        return saveForLater;
    }

    public ShoppingCart saveForLater(String saveForLater) {
        this.saveForLater = saveForLater;
        return this;
    }

    public void setSaveForLater(String saveForLater) {
        this.saveForLater = saveForLater;
    }

    public Employee getCart() {
        return cart;
    }

    public ShoppingCart cart(Employee employee) {
        this.cart = employee;
        return this;
    }

    public void setCart(Employee employee) {
        this.cart = employee;
    }

    public CartItem getCartItem() {
        return cartItem;
    }

    public ShoppingCart cartItem(CartItem cartItem) {
        this.cartItem = cartItem;
        return this;
    }

    public void setCartItem(CartItem cartItem) {
        this.cartItem = cartItem;
    }
    // jhipster-needle-entity-add-getters-setters - Jhipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ShoppingCart shoppingCart = (ShoppingCart) o;
        if (shoppingCart.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), shoppingCart.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ShoppingCart{" +
            "id=" + getId() +
            ", shoppingCartId='" + getShoppingCartId() + "'" +
            ", cartStatus='" + getCartStatus() + "'" +
            ", saveForLater='" + getSaveForLater() + "'" +
            "}";
    }
}
