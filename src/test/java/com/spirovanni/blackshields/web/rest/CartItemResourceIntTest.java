package com.spirovanni.blackshields.web.rest;

import com.spirovanni.blackshields.ArmoryApp;

import com.spirovanni.blackshields.domain.CartItem;
import com.spirovanni.blackshields.repository.CartItemRepository;
import com.spirovanni.blackshields.service.CartItemService;
import com.spirovanni.blackshields.repository.search.CartItemSearchRepository;
import com.spirovanni.blackshields.service.dto.CartItemDTO;
import com.spirovanni.blackshields.service.mapper.CartItemMapper;
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

import com.spirovanni.blackshields.domain.enumeration.DepartmentMajor;
/**
 * Test class for the CartItemResource REST controller.
 *
 * @see CartItemResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ArmoryApp.class)
public class CartItemResourceIntTest {

    private static final Long DEFAULT_CART_ITEM_ID = 1L;
    private static final Long UPDATED_CART_ITEM_ID = 2L;

    private static final String DEFAULT_PRODUCT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_PRODUCT_PRICE = 1L;
    private static final Long UPDATED_PRODUCT_PRICE = 2L;

    private static final DepartmentMajor DEFAULT_DEPARTMENT_MAJOR = DepartmentMajor.APPS;
    private static final DepartmentMajor UPDATED_DEPARTMENT_MAJOR = DepartmentMajor.BEAUTY;

    private static final String DEFAULT_PRODUCT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private CartItemMapper cartItemMapper;

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private CartItemSearchRepository cartItemSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCartItemMockMvc;

    private CartItem cartItem;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CartItemResource cartItemResource = new CartItemResource(cartItemService);
        this.restCartItemMockMvc = MockMvcBuilders.standaloneSetup(cartItemResource)
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
    public static CartItem createEntity(EntityManager em) {
        CartItem cartItem = new CartItem()
            .cartItemId(DEFAULT_CART_ITEM_ID)
            .productName(DEFAULT_PRODUCT_NAME)
            .productPrice(DEFAULT_PRODUCT_PRICE)
            .departmentMajor(DEFAULT_DEPARTMENT_MAJOR)
            .productDescription(DEFAULT_PRODUCT_DESCRIPTION);
        return cartItem;
    }

    @Before
    public void initTest() {
        cartItemSearchRepository.deleteAll();
        cartItem = createEntity(em);
    }

    @Test
    @Transactional
    public void createCartItem() throws Exception {
        int databaseSizeBeforeCreate = cartItemRepository.findAll().size();

        // Create the CartItem
        CartItemDTO cartItemDTO = cartItemMapper.toDto(cartItem);
        restCartItemMockMvc.perform(post("/api/cart-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cartItemDTO)))
            .andExpect(status().isCreated());

        // Validate the CartItem in the database
        List<CartItem> cartItemList = cartItemRepository.findAll();
        assertThat(cartItemList).hasSize(databaseSizeBeforeCreate + 1);
        CartItem testCartItem = cartItemList.get(cartItemList.size() - 1);
        assertThat(testCartItem.getCartItemId()).isEqualTo(DEFAULT_CART_ITEM_ID);
        assertThat(testCartItem.getProductName()).isEqualTo(DEFAULT_PRODUCT_NAME);
        assertThat(testCartItem.getProductPrice()).isEqualTo(DEFAULT_PRODUCT_PRICE);
        assertThat(testCartItem.getDepartmentMajor()).isEqualTo(DEFAULT_DEPARTMENT_MAJOR);
        assertThat(testCartItem.getProductDescription()).isEqualTo(DEFAULT_PRODUCT_DESCRIPTION);

        // Validate the CartItem in Elasticsearch
        CartItem cartItemEs = cartItemSearchRepository.findOne(testCartItem.getId());
        assertThat(cartItemEs).isEqualToComparingFieldByField(testCartItem);
    }

    @Test
    @Transactional
    public void createCartItemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cartItemRepository.findAll().size();

        // Create the CartItem with an existing ID
        cartItem.setId(1L);
        CartItemDTO cartItemDTO = cartItemMapper.toDto(cartItem);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCartItemMockMvc.perform(post("/api/cart-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cartItemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CartItem> cartItemList = cartItemRepository.findAll();
        assertThat(cartItemList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCartItems() throws Exception {
        // Initialize the database
        cartItemRepository.saveAndFlush(cartItem);

        // Get all the cartItemList
        restCartItemMockMvc.perform(get("/api/cart-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cartItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].cartItemId").value(hasItem(DEFAULT_CART_ITEM_ID.intValue())))
            .andExpect(jsonPath("$.[*].productName").value(hasItem(DEFAULT_PRODUCT_NAME.toString())))
            .andExpect(jsonPath("$.[*].productPrice").value(hasItem(DEFAULT_PRODUCT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].departmentMajor").value(hasItem(DEFAULT_DEPARTMENT_MAJOR.toString())))
            .andExpect(jsonPath("$.[*].productDescription").value(hasItem(DEFAULT_PRODUCT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getCartItem() throws Exception {
        // Initialize the database
        cartItemRepository.saveAndFlush(cartItem);

        // Get the cartItem
        restCartItemMockMvc.perform(get("/api/cart-items/{id}", cartItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cartItem.getId().intValue()))
            .andExpect(jsonPath("$.cartItemId").value(DEFAULT_CART_ITEM_ID.intValue()))
            .andExpect(jsonPath("$.productName").value(DEFAULT_PRODUCT_NAME.toString()))
            .andExpect(jsonPath("$.productPrice").value(DEFAULT_PRODUCT_PRICE.intValue()))
            .andExpect(jsonPath("$.departmentMajor").value(DEFAULT_DEPARTMENT_MAJOR.toString()))
            .andExpect(jsonPath("$.productDescription").value(DEFAULT_PRODUCT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCartItem() throws Exception {
        // Get the cartItem
        restCartItemMockMvc.perform(get("/api/cart-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCartItem() throws Exception {
        // Initialize the database
        cartItemRepository.saveAndFlush(cartItem);
        cartItemSearchRepository.save(cartItem);
        int databaseSizeBeforeUpdate = cartItemRepository.findAll().size();

        // Update the cartItem
        CartItem updatedCartItem = cartItemRepository.findOne(cartItem.getId());
        updatedCartItem
            .cartItemId(UPDATED_CART_ITEM_ID)
            .productName(UPDATED_PRODUCT_NAME)
            .productPrice(UPDATED_PRODUCT_PRICE)
            .departmentMajor(UPDATED_DEPARTMENT_MAJOR)
            .productDescription(UPDATED_PRODUCT_DESCRIPTION);
        CartItemDTO cartItemDTO = cartItemMapper.toDto(updatedCartItem);

        restCartItemMockMvc.perform(put("/api/cart-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cartItemDTO)))
            .andExpect(status().isOk());

        // Validate the CartItem in the database
        List<CartItem> cartItemList = cartItemRepository.findAll();
        assertThat(cartItemList).hasSize(databaseSizeBeforeUpdate);
        CartItem testCartItem = cartItemList.get(cartItemList.size() - 1);
        assertThat(testCartItem.getCartItemId()).isEqualTo(UPDATED_CART_ITEM_ID);
        assertThat(testCartItem.getProductName()).isEqualTo(UPDATED_PRODUCT_NAME);
        assertThat(testCartItem.getProductPrice()).isEqualTo(UPDATED_PRODUCT_PRICE);
        assertThat(testCartItem.getDepartmentMajor()).isEqualTo(UPDATED_DEPARTMENT_MAJOR);
        assertThat(testCartItem.getProductDescription()).isEqualTo(UPDATED_PRODUCT_DESCRIPTION);

        // Validate the CartItem in Elasticsearch
        CartItem cartItemEs = cartItemSearchRepository.findOne(testCartItem.getId());
        assertThat(cartItemEs).isEqualToComparingFieldByField(testCartItem);
    }

    @Test
    @Transactional
    public void updateNonExistingCartItem() throws Exception {
        int databaseSizeBeforeUpdate = cartItemRepository.findAll().size();

        // Create the CartItem
        CartItemDTO cartItemDTO = cartItemMapper.toDto(cartItem);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCartItemMockMvc.perform(put("/api/cart-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cartItemDTO)))
            .andExpect(status().isCreated());

        // Validate the CartItem in the database
        List<CartItem> cartItemList = cartItemRepository.findAll();
        assertThat(cartItemList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCartItem() throws Exception {
        // Initialize the database
        cartItemRepository.saveAndFlush(cartItem);
        cartItemSearchRepository.save(cartItem);
        int databaseSizeBeforeDelete = cartItemRepository.findAll().size();

        // Get the cartItem
        restCartItemMockMvc.perform(delete("/api/cart-items/{id}", cartItem.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean cartItemExistsInEs = cartItemSearchRepository.exists(cartItem.getId());
        assertThat(cartItemExistsInEs).isFalse();

        // Validate the database is empty
        List<CartItem> cartItemList = cartItemRepository.findAll();
        assertThat(cartItemList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchCartItem() throws Exception {
        // Initialize the database
        cartItemRepository.saveAndFlush(cartItem);
        cartItemSearchRepository.save(cartItem);

        // Search the cartItem
        restCartItemMockMvc.perform(get("/api/_search/cart-items?query=id:" + cartItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cartItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].cartItemId").value(hasItem(DEFAULT_CART_ITEM_ID.intValue())))
            .andExpect(jsonPath("$.[*].productName").value(hasItem(DEFAULT_PRODUCT_NAME.toString())))
            .andExpect(jsonPath("$.[*].productPrice").value(hasItem(DEFAULT_PRODUCT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].departmentMajor").value(hasItem(DEFAULT_DEPARTMENT_MAJOR.toString())))
            .andExpect(jsonPath("$.[*].productDescription").value(hasItem(DEFAULT_PRODUCT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CartItem.class);
        CartItem cartItem1 = new CartItem();
        cartItem1.setId(1L);
        CartItem cartItem2 = new CartItem();
        cartItem2.setId(cartItem1.getId());
        assertThat(cartItem1).isEqualTo(cartItem2);
        cartItem2.setId(2L);
        assertThat(cartItem1).isNotEqualTo(cartItem2);
        cartItem1.setId(null);
        assertThat(cartItem1).isNotEqualTo(cartItem2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CartItemDTO.class);
        CartItemDTO cartItemDTO1 = new CartItemDTO();
        cartItemDTO1.setId(1L);
        CartItemDTO cartItemDTO2 = new CartItemDTO();
        assertThat(cartItemDTO1).isNotEqualTo(cartItemDTO2);
        cartItemDTO2.setId(cartItemDTO1.getId());
        assertThat(cartItemDTO1).isEqualTo(cartItemDTO2);
        cartItemDTO2.setId(2L);
        assertThat(cartItemDTO1).isNotEqualTo(cartItemDTO2);
        cartItemDTO1.setId(null);
        assertThat(cartItemDTO1).isNotEqualTo(cartItemDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(cartItemMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(cartItemMapper.fromId(null)).isNull();
    }
}
