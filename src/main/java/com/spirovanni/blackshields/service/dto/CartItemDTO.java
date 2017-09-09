package com.spirovanni.blackshields.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import com.spirovanni.blackshields.domain.enumeration.DepartmentMajor;

/**
 * A DTO for the CartItem entity.
 */
public class CartItemDTO implements Serializable {

    private Long id;

    private Long cartItemId;

    private String productName;

    private Long productPrice;

    private DepartmentMajor departmentMajor;

    private String productDescription;

    private Long bookId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(Long cartItemId) {
        this.cartItemId = cartItemId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Long getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Long productPrice) {
        this.productPrice = productPrice;
    }

    public DepartmentMajor getDepartmentMajor() {
        return departmentMajor;
    }

    public void setDepartmentMajor(DepartmentMajor departmentMajor) {
        this.departmentMajor = departmentMajor;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CartItemDTO cartItemDTO = (CartItemDTO) o;
        if(cartItemDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cartItemDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CartItemDTO{" +
            "id=" + getId() +
            ", cartItemId='" + getCartItemId() + "'" +
            ", productName='" + getProductName() + "'" +
            ", productPrice='" + getProductPrice() + "'" +
            ", departmentMajor='" + getDepartmentMajor() + "'" +
            ", productDescription='" + getProductDescription() + "'" +
            "}";
    }
}
