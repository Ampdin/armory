package com.spirovanni.blackshields.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import com.spirovanni.blackshields.domain.enumeration.Language;

/**
 * A DTO for the Book entity.
 */
public class BookDTO implements Serializable {

    private Long id;

    private Long bookId;

    private String bookName;

    private Long bookPrice;

    private String publisher;

    private Language language;

    private String isbn10;

    private String isbn13;

    private String productDimensions;

    private String shippingWeight;

    private String ranking;

    private String averageRanking;

    private String author;

    private String subject;

    private String bookDescription;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public Long getBookPrice() {
        return bookPrice;
    }

    public void setBookPrice(Long bookPrice) {
        this.bookPrice = bookPrice;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public String getIsbn10() {
        return isbn10;
    }

    public void setIsbn10(String isbn10) {
        this.isbn10 = isbn10;
    }

    public String getIsbn13() {
        return isbn13;
    }

    public void setIsbn13(String isbn13) {
        this.isbn13 = isbn13;
    }

    public String getProductDimensions() {
        return productDimensions;
    }

    public void setProductDimensions(String productDimensions) {
        this.productDimensions = productDimensions;
    }

    public String getShippingWeight() {
        return shippingWeight;
    }

    public void setShippingWeight(String shippingWeight) {
        this.shippingWeight = shippingWeight;
    }

    public String getRanking() {
        return ranking;
    }

    public void setRanking(String ranking) {
        this.ranking = ranking;
    }

    public String getAverageRanking() {
        return averageRanking;
    }

    public void setAverageRanking(String averageRanking) {
        this.averageRanking = averageRanking;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBookDescription() {
        return bookDescription;
    }

    public void setBookDescription(String bookDescription) {
        this.bookDescription = bookDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BookDTO bookDTO = (BookDTO) o;
        if(bookDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), bookDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BookDTO{" +
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
