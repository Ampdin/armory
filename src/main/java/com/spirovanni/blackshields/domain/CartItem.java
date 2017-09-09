package com.spirovanni.blackshields.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.spirovanni.blackshields.domain.enumeration.DepartmentMajor;

/**
 * A CartItem.
 */
@Entity
@Table(name = "cart_item")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "cartitem")
public class CartItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cart_item_id")
    private Long cartItemId;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "product_price")
    private Long productPrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "department_major")
    private DepartmentMajor departmentMajor;

    @Column(name = "product_description")
    private String productDescription;

    /**
     * Where we fill the Cart
     */
    @ApiModelProperty(value = "Where we fill the Cart")
    @OneToMany(mappedBy = "cartItem")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ShoppingCart> shoppingCarts = new HashSet<>();

    @ManyToOne
    private Book book;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCartItemId() {
        return cartItemId;
    }

    public CartItem cartItemId(Long cartItemId) {
        this.cartItemId = cartItemId;
        return this;
    }

    public void setCartItemId(Long cartItemId) {
        this.cartItemId = cartItemId;
    }

    public String getProductName() {
        return productName;
    }

    public CartItem productName(String productName) {
        this.productName = productName;
        return this;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Long getProductPrice() {
        return productPrice;
    }

    public CartItem productPrice(Long productPrice) {
        this.productPrice = productPrice;
        return this;
    }

    public void setProductPrice(Long productPrice) {
        this.productPrice = productPrice;
    }

    public DepartmentMajor getDepartmentMajor() {
        return departmentMajor;
    }

    public CartItem departmentMajor(DepartmentMajor departmentMajor) {
        this.departmentMajor = departmentMajor;
        return this;
    }

    public void setDepartmentMajor(DepartmentMajor departmentMajor) {
        this.departmentMajor = departmentMajor;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public CartItem productDescription(String productDescription) {
        this.productDescription = productDescription;
        return this;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public Set<ShoppingCart> getShoppingCarts() {
        return shoppingCarts;
    }

    public CartItem shoppingCarts(Set<ShoppingCart> shoppingCarts) {
        this.shoppingCarts = shoppingCarts;
        return this;
    }

    public CartItem addShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCarts.add(shoppingCart);
        shoppingCart.setCartItem(this);
        return this;
    }

    public CartItem removeShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCarts.remove(shoppingCart);
        shoppingCart.setCartItem(null);
        return this;
    }

    public void setShoppingCarts(Set<ShoppingCart> shoppingCarts) {
        this.shoppingCarts = shoppingCarts;
    }

    public Book getBook() {
        return book;
    }

    public CartItem book(Book book) {
        this.book = book;
        return this;
    }

    public void setBook(Book book) {
        this.book = book;
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
        CartItem cartItem = (CartItem) o;
        if (cartItem.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cartItem.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CartItem{" +
            "id=" + getId() +
            ", cartItemId='" + getCartItemId() + "'" +
            ", productName='" + getProductName() + "'" +
            ", productPrice='" + getProductPrice() + "'" +
            ", departmentMajor='" + getDepartmentMajor() + "'" +
            ", productDescription='" + getProductDescription() + "'" +
            "}";
    }
}
