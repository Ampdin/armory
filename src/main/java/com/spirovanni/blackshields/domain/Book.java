package com.spirovanni.blackshields.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.spirovanni.blackshields.domain.enumeration.Language;

/**
 * A Book.
 */
@Entity
@Table(name = "book")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "book")
public class Book implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "book_id")
    private Long bookId;

    @Column(name = "book_name")
    private String bookName;

    @Column(name = "book_price")
    private Long bookPrice;

    @Column(name = "publisher")
    private String publisher;

    @Enumerated(EnumType.STRING)
    @Column(name = "language")
    private Language language;

    @Column(name = "isbn_10")
    private String isbn10;

    @Column(name = "isbn_13")
    private String isbn13;

    @Column(name = "product_dimensions")
    private String productDimensions;

    @Column(name = "shipping_weight")
    private String shippingWeight;

    @Column(name = "ranking")
    private String ranking;

    @Column(name = "average_ranking")
    private String averageRanking;

    @Column(name = "author")
    private String author;

    @Column(name = "subject")
    private String subject;

    @Column(name = "book_description")
    private String bookDescription;

    @OneToMany(mappedBy = "book")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CartItem> cartItems = new HashSet<>();

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBookId() {
        return bookId;
    }

    public Book bookId(Long bookId) {
        this.bookId = bookId;
        return this;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public Book bookName(String bookName) {
        this.bookName = bookName;
        return this;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public Long getBookPrice() {
        return bookPrice;
    }

    public Book bookPrice(Long bookPrice) {
        this.bookPrice = bookPrice;
        return this;
    }

    public void setBookPrice(Long bookPrice) {
        this.bookPrice = bookPrice;
    }

    public String getPublisher() {
        return publisher;
    }

    public Book publisher(String publisher) {
        this.publisher = publisher;
        return this;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Language getLanguage() {
        return language;
    }

    public Book language(Language language) {
        this.language = language;
        return this;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public String getIsbn10() {
        return isbn10;
    }

    public Book isbn10(String isbn10) {
        this.isbn10 = isbn10;
        return this;
    }

    public void setIsbn10(String isbn10) {
        this.isbn10 = isbn10;
    }

    public String getIsbn13() {
        return isbn13;
    }

    public Book isbn13(String isbn13) {
        this.isbn13 = isbn13;
        return this;
    }

    public void setIsbn13(String isbn13) {
        this.isbn13 = isbn13;
    }

    public String getProductDimensions() {
        return productDimensions;
    }

    public Book productDimensions(String productDimensions) {
        this.productDimensions = productDimensions;
        return this;
    }

    public void setProductDimensions(String productDimensions) {
        this.productDimensions = productDimensions;
    }

    public String getShippingWeight() {
        return shippingWeight;
    }

    public Book shippingWeight(String shippingWeight) {
        this.shippingWeight = shippingWeight;
        return this;
    }

    public void setShippingWeight(String shippingWeight) {
        this.shippingWeight = shippingWeight;
    }

    public String getRanking() {
        return ranking;
    }

    public Book ranking(String ranking) {
        this.ranking = ranking;
        return this;
    }

    public void setRanking(String ranking) {
        this.ranking = ranking;
    }

    public String getAverageRanking() {
        return averageRanking;
    }

    public Book averageRanking(String averageRanking) {
        this.averageRanking = averageRanking;
        return this;
    }

    public void setAverageRanking(String averageRanking) {
        this.averageRanking = averageRanking;
    }

    public String getAuthor() {
        return author;
    }

    public Book author(String author) {
        this.author = author;
        return this;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSubject() {
        return subject;
    }

    public Book subject(String subject) {
        this.subject = subject;
        return this;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBookDescription() {
        return bookDescription;
    }

    public Book bookDescription(String bookDescription) {
        this.bookDescription = bookDescription;
        return this;
    }

    public void setBookDescription(String bookDescription) {
        this.bookDescription = bookDescription;
    }

    public Set<CartItem> getCartItems() {
        return cartItems;
    }

    public Book cartItems(Set<CartItem> cartItems) {
        this.cartItems = cartItems;
        return this;
    }

    public Book addCartItem(CartItem cartItem) {
        this.cartItems.add(cartItem);
        cartItem.setBook(this);
        return this;
    }

    public Book removeCartItem(CartItem cartItem) {
        this.cartItems.remove(cartItem);
        cartItem.setBook(null);
        return this;
    }

    public void setCartItems(Set<CartItem> cartItems) {
        this.cartItems = cartItems;
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
        Book book = (Book) o;
        if (book.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), book.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Book{" +
            "id=" + getId() +
            ", bookId='" + getBookId() + "'" +
            ", bookName='" + getBookName() + "'" +
            ", bookPrice='" + getBookPrice() + "'" +
            ", publisher='" + getPublisher() + "'" +
            ", language='" + getLanguage() + "'" +
            ", isbn10='" + getIsbn10() + "'" +
            ", isbn13='" + getIsbn13() + "'" +
            ", productDimensions='" + getProductDimensions() + "'" +
            ", shippingWeight='" + getShippingWeight() + "'" +
            ", ranking='" + getRanking() + "'" +
            ", averageRanking='" + getAverageRanking() + "'" +
            ", author='" + getAuthor() + "'" +
            ", subject='" + getSubject() + "'" +
            ", bookDescription='" + getBookDescription() + "'" +
            "}";
    }
}
