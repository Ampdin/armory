package com.spirovanni.blackshields.web.rest;

import com.spirovanni.blackshields.ArmoryApp;

import com.spirovanni.blackshields.domain.Book;
import com.spirovanni.blackshields.repository.BookRepository;
import com.spirovanni.blackshields.service.BookService;
import com.spirovanni.blackshields.repository.search.BookSearchRepository;
import com.spirovanni.blackshields.service.dto.BookDTO;
import com.spirovanni.blackshields.service.mapper.BookMapper;
import com.spirovanni.blackshields.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.spirovanni.blackshields.domain.enumeration.Language;
/**
 * Test class for the BookResource REST controller.
 *
 * @see BookResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ArmoryApp.class)
public class BookResourceIntTest {

    private static final Long DEFAULT_BOOK_ID = 1L;
    private static final Long UPDATED_BOOK_ID = 2L;

    private static final String DEFAULT_BOOK_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BOOK_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_BOOK_PRICE = 1L;
    private static final Long UPDATED_BOOK_PRICE = 2L;

    private static final String DEFAULT_PUBLISHER = "AAAAAAAAAA";
    private static final String UPDATED_PUBLISHER = "BBBBBBBBBB";

    private static final Language DEFAULT_LANGUAGE = Language.FRENCH;
    private static final Language UPDATED_LANGUAGE = Language.ENGLISH;

    private static final String DEFAULT_ISBN_10 = "AAAAAAAAAA";
    private static final String UPDATED_ISBN_10 = "BBBBBBBBBB";

    private static final String DEFAULT_ISBN_13 = "AAAAAAAAAA";
    private static final String UPDATED_ISBN_13 = "BBBBBBBBBB";

    private static final String DEFAULT_PRODUCT_DIMENSIONS = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_DIMENSIONS = "BBBBBBBBBB";

    private static final String DEFAULT_SHIPPING_WEIGHT = "AAAAAAAAAA";
    private static final String UPDATED_SHIPPING_WEIGHT = "BBBBBBBBBB";

    private static final String DEFAULT_RANKING = "AAAAAAAAAA";
    private static final String UPDATED_RANKING = "BBBBBBBBBB";

    private static final String DEFAULT_AVERAGE_RANKING = "AAAAAAAAAA";
    private static final String UPDATED_AVERAGE_RANKING = "BBBBBBBBBB";

    private static final String DEFAULT_AUTHOR = "AAAAAAAAAA";
    private static final String UPDATED_AUTHOR = "BBBBBBBBBB";

    private static final String DEFAULT_SUBJECT = "AAAAAAAAAA";
    private static final String UPDATED_SUBJECT = "BBBBBBBBBB";

    private static final String DEFAULT_BOOK_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_BOOK_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private BookService bookService;

    @Autowired
    private BookSearchRepository bookSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBookMockMvc;

    private Book book;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BookResource bookResource = new BookResource(bookService);
        this.restBookMockMvc = MockMvcBuilders.standaloneSetup(bookResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Book createEntity(EntityManager em) {
        Book book = new Book()
            .bookId(DEFAULT_BOOK_ID)
            .bookName(DEFAULT_BOOK_NAME)
            .bookPrice(DEFAULT_BOOK_PRICE)
            .publisher(DEFAULT_PUBLISHER)
            .language(DEFAULT_LANGUAGE)
            .isbn10(DEFAULT_ISBN_10)
            .isbn13(DEFAULT_ISBN_13)
            .productDimensions(DEFAULT_PRODUCT_DIMENSIONS)
            .shippingWeight(DEFAULT_SHIPPING_WEIGHT)
            .ranking(DEFAULT_RANKING)
            .averageRanking(DEFAULT_AVERAGE_RANKING)
            .author(DEFAULT_AUTHOR)
            .subject(DEFAULT_SUBJECT)
            .bookDescription(DEFAULT_BOOK_DESCRIPTION);
        return book;
    }

    @Before
    public void initTest() {
        bookSearchRepository.deleteAll();
        book = createEntity(em);
    }

    @Test
    @Transactional
    public void createBook() throws Exception {
        int databaseSizeBeforeCreate = bookRepository.findAll().size();

        // Create the Book
        BookDTO bookDTO = bookMapper.toDto(book);
        restBookMockMvc.perform(post("/api/books")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bookDTO)))
            .andExpect(status().isCreated());

        // Validate the Book in the database
        List<Book> bookList = bookRepository.findAll();
        assertThat(bookList).hasSize(databaseSizeBeforeCreate + 1);
        Book testBook = bookList.get(bookList.size() - 1);
        assertThat(testBook.getBookId()).isEqualTo(DEFAULT_BOOK_ID);
        assertThat(testBook.getBookName()).isEqualTo(DEFAULT_BOOK_NAME);
        assertThat(testBook.getBookPrice()).isEqualTo(DEFAULT_BOOK_PRICE);
        assertThat(testBook.getPublisher()).isEqualTo(DEFAULT_PUBLISHER);
        assertThat(testBook.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
        assertThat(testBook.getIsbn10()).isEqualTo(DEFAULT_ISBN_10);
        assertThat(testBook.getIsbn13()).isEqualTo(DEFAULT_ISBN_13);
        assertThat(testBook.getProductDimensions()).isEqualTo(DEFAULT_PRODUCT_DIMENSIONS);
        assertThat(testBook.getShippingWeight()).isEqualTo(DEFAULT_SHIPPING_WEIGHT);
        assertThat(testBook.getRanking()).isEqualTo(DEFAULT_RANKING);
        assertThat(testBook.getAverageRanking()).isEqualTo(DEFAULT_AVERAGE_RANKING);
        assertThat(testBook.getAuthor()).isEqualTo(DEFAULT_AUTHOR);
        assertThat(testBook.getSubject()).isEqualTo(DEFAULT_SUBJECT);
        assertThat(testBook.getBookDescription()).isEqualTo(DEFAULT_BOOK_DESCRIPTION);

        // Validate the Book in Elasticsearch
        Book bookEs = bookSearchRepository.findOne(testBook.getId());
        assertThat(bookEs).isEqualToComparingFieldByField(testBook);
    }

    @Test
    @Transactional
    public void createBookWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bookRepository.findAll().size();

        // Create the Book with an existing ID
        book.setId(1L);
        BookDTO bookDTO = bookMapper.toDto(book);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBookMockMvc.perform(post("/api/books")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bookDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Book> bookList = bookRepository.findAll();
        assertThat(bookList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllBooks() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList
        restBookMockMvc.perform(get("/api/books?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(book.getId().intValue())))
            .andExpect(jsonPath("$.[*].bookId").value(hasItem(DEFAULT_BOOK_ID.intValue())))
            .andExpect(jsonPath("$.[*].bookName").value(hasItem(DEFAULT_BOOK_NAME.toString())))
            .andExpect(jsonPath("$.[*].bookPrice").value(hasItem(DEFAULT_BOOK_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].publisher").value(hasItem(DEFAULT_PUBLISHER.toString())))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE.toString())))
            .andExpect(jsonPath("$.[*].isbn10").value(hasItem(DEFAULT_ISBN_10.toString())))
            .andExpect(jsonPath("$.[*].isbn13").value(hasItem(DEFAULT_ISBN_13.toString())))
            .andExpect(jsonPath("$.[*].productDimensions").value(hasItem(DEFAULT_PRODUCT_DIMENSIONS.toString())))
            .andExpect(jsonPath("$.[*].shippingWeight").value(hasItem(DEFAULT_SHIPPING_WEIGHT.toString())))
            .andExpect(jsonPath("$.[*].ranking").value(hasItem(DEFAULT_RANKING.toString())))
            .andExpect(jsonPath("$.[*].averageRanking").value(hasItem(DEFAULT_AVERAGE_RANKING.toString())))
            .andExpect(jsonPath("$.[*].author").value(hasItem(DEFAULT_AUTHOR.toString())))
            .andExpect(jsonPath("$.[*].subject").value(hasItem(DEFAULT_SUBJECT.toString())))
            .andExpect(jsonPath("$.[*].bookDescription").value(hasItem(DEFAULT_BOOK_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getBook() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get the book
        restBookMockMvc.perform(get("/api/books/{id}", book.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(book.getId().intValue()))
            .andExpect(jsonPath("$.bookId").value(DEFAULT_BOOK_ID.intValue()))
            .andExpect(jsonPath("$.bookName").value(DEFAULT_BOOK_NAME.toString()))
            .andExpect(jsonPath("$.bookPrice").value(DEFAULT_BOOK_PRICE.intValue()))
            .andExpect(jsonPath("$.publisher").value(DEFAULT_PUBLISHER.toString()))
            .andExpect(jsonPath("$.language").value(DEFAULT_LANGUAGE.toString()))
            .andExpect(jsonPath("$.isbn10").value(DEFAULT_ISBN_10.toString()))
            .andExpect(jsonPath("$.isbn13").value(DEFAULT_ISBN_13.toString()))
            .andExpect(jsonPath("$.productDimensions").value(DEFAULT_PRODUCT_DIMENSIONS.toString()))
            .andExpect(jsonPath("$.shippingWeight").value(DEFAULT_SHIPPING_WEIGHT.toString()))
            .andExpect(jsonPath("$.ranking").value(DEFAULT_RANKING.toString()))
            .andExpect(jsonPath("$.averageRanking").value(DEFAULT_AVERAGE_RANKING.toString()))
            .andExpect(jsonPath("$.author").value(DEFAULT_AUTHOR.toString()))
            .andExpect(jsonPath("$.subject").value(DEFAULT_SUBJECT.toString()))
            .andExpect(jsonPath("$.bookDescription").value(DEFAULT_BOOK_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBook() throws Exception {
        // Get the book
        restBookMockMvc.perform(get("/api/books/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBook() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);
        bookSearchRepository.save(book);
        int databaseSizeBeforeUpdate = bookRepository.findAll().size();

        // Update the book
        Book updatedBook = bookRepository.findOne(book.getId());
        updatedBook
            .bookId(UPDATED_BOOK_ID)
            .bookName(UPDATED_BOOK_NAME)
            .bookPrice(UPDATED_BOOK_PRICE)
            .publisher(UPDATED_PUBLISHER)
            .language(UPDATED_LANGUAGE)
            .isbn10(UPDATED_ISBN_10)
            .isbn13(UPDATED_ISBN_13)
            .productDimensions(UPDATED_PRODUCT_DIMENSIONS)
            .shippingWeight(UPDATED_SHIPPING_WEIGHT)
            .ranking(UPDATED_RANKING)
            .averageRanking(UPDATED_AVERAGE_RANKING)
            .author(UPDATED_AUTHOR)
            .subject(UPDATED_SUBJECT)
            .bookDescription(UPDATED_BOOK_DESCRIPTION);
        BookDTO bookDTO = bookMapper.toDto(updatedBook);

        restBookMockMvc.perform(put("/api/books")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bookDTO)))
            .andExpect(status().isOk());

        // Validate the Book in the database
        List<Book> bookList = bookRepository.findAll();
        assertThat(bookList).hasSize(databaseSizeBeforeUpdate);
        Book testBook = bookList.get(bookList.size() - 1);
        assertThat(testBook.getBookId()).isEqualTo(UPDATED_BOOK_ID);
        assertThat(testBook.getBookName()).isEqualTo(UPDATED_BOOK_NAME);
        assertThat(testBook.getBookPrice()).isEqualTo(UPDATED_BOOK_PRICE);
        assertThat(testBook.getPublisher()).isEqualTo(UPDATED_PUBLISHER);
        assertThat(testBook.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
        assertThat(testBook.getIsbn10()).isEqualTo(UPDATED_ISBN_10);
        assertThat(testBook.getIsbn13()).isEqualTo(UPDATED_ISBN_13);
        assertThat(testBook.getProductDimensions()).isEqualTo(UPDATED_PRODUCT_DIMENSIONS);
        assertThat(testBook.getShippingWeight()).isEqualTo(UPDATED_SHIPPING_WEIGHT);
        assertThat(testBook.getRanking()).isEqualTo(UPDATED_RANKING);
        assertThat(testBook.getAverageRanking()).isEqualTo(UPDATED_AVERAGE_RANKING);
        assertThat(testBook.getAuthor()).isEqualTo(UPDATED_AUTHOR);
        assertThat(testBook.getSubject()).isEqualTo(UPDATED_SUBJECT);
        assertThat(testBook.getBookDescription()).isEqualTo(UPDATED_BOOK_DESCRIPTION);

        // Validate the Book in Elasticsearch
        Book bookEs = bookSearchRepository.findOne(testBook.getId());
        assertThat(bookEs).isEqualToComparingFieldByField(testBook);
    }

    @Test
    @Transactional
    public void updateNonExistingBook() throws Exception {
        int databaseSizeBeforeUpdate = bookRepository.findAll().size();

        // Create the Book
        BookDTO bookDTO = bookMapper.toDto(book);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBookMockMvc.perform(put("/api/books")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bookDTO)))
            .andExpect(status().isCreated());

        // Validate the Book in the database
        List<Book> bookList = bookRepository.findAll();
        assertThat(bookList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBook() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);
        bookSearchRepository.save(book);
        int databaseSizeBeforeDelete = bookRepository.findAll().size();

        // Get the book
        restBookMockMvc.perform(delete("/api/books/{id}", book.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean bookExistsInEs = bookSearchRepository.exists(book.getId());
        assertThat(bookExistsInEs).isFalse();

        // Validate the database is empty
        List<Book> bookList = bookRepository.findAll();
        assertThat(bookList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchBook() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);
        bookSearchRepository.save(book);

        // Search the book
        restBookMockMvc.perform(get("/api/_search/books?query=id:" + book.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(book.getId().intValue())))
            .andExpect(jsonPath("$.[*].bookId").value(hasItem(DEFAULT_BOOK_ID.intValue())))
            .andExpect(jsonPath("$.[*].bookName").value(hasItem(DEFAULT_BOOK_NAME.toString())))
            .andExpect(jsonPath("$.[*].bookPrice").value(hasItem(DEFAULT_BOOK_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].publisher").value(hasItem(DEFAULT_PUBLISHER.toString())))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE.toString())))
            .andExpect(jsonPath("$.[*].isbn10").value(hasItem(DEFAULT_ISBN_10.toString())))
            .andExpect(jsonPath("$.[*].isbn13").value(hasItem(DEFAULT_ISBN_13.toString())))
            .andExpect(jsonPath("$.[*].productDimensions").value(hasItem(DEFAULT_PRODUCT_DIMENSIONS.toString())))
            .andExpect(jsonPath("$.[*].shippingWeight").value(hasItem(DEFAULT_SHIPPING_WEIGHT.toString())))
            .andExpect(jsonPath("$.[*].ranking").value(hasItem(DEFAULT_RANKING.toString())))
            .andExpect(jsonPath("$.[*].averageRanking").value(hasItem(DEFAULT_AVERAGE_RANKING.toString())))
            .andExpect(jsonPath("$.[*].author").value(hasItem(DEFAULT_AUTHOR.toString())))
            .andExpect(jsonPath("$.[*].subject").value(hasItem(DEFAULT_SUBJECT.toString())))
            .andExpect(jsonPath("$.[*].bookDescription").value(hasItem(DEFAULT_BOOK_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Book.class);
        Book book1 = new Book();
        book1.setId(1L);
        Book book2 = new Book();
        book2.setId(book1.getId());
        assertThat(book1).isEqualTo(book2);
        book2.setId(2L);
        assertThat(book1).isNotEqualTo(book2);
        book1.setId(null);
        assertThat(book1).isNotEqualTo(book2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BookDTO.class);
        BookDTO bookDTO1 = new BookDTO();
        bookDTO1.setId(1L);
        BookDTO bookDTO2 = new BookDTO();
        assertThat(bookDTO1).isNotEqualTo(bookDTO2);
        bookDTO2.setId(bookDTO1.getId());
        assertThat(bookDTO1).isEqualTo(bookDTO2);
        bookDTO2.setId(2L);
        assertThat(bookDTO1).isNotEqualTo(bookDTO2);
        bookDTO1.setId(null);
        assertThat(bookDTO1).isNotEqualTo(bookDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(bookMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(bookMapper.fromId(null)).isNull();
    }
}
