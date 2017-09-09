package com.spirovanni.blackshields.web.rest;

import com.spirovanni.blackshields.ArmoryApp;

import com.spirovanni.blackshields.domain.ShoppingCart;
import com.spirovanni.blackshields.repository.ShoppingCartRepository;
import com.spirovanni.blackshields.service.ShoppingCartService;
import com.spirovanni.blackshields.repository.search.ShoppingCartSearchRepository;
import com.spirovanni.blackshields.service.dto.ShoppingCartDTO;
import com.spirovanni.blackshields.service.mapper.ShoppingCartMapper;
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

/**
 * Test class for the ShoppingCartResource REST controller.
 *
 * @see ShoppingCartResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ArmoryApp.class)
public class ShoppingCartResourceIntTest {

    private static final Long DEFAULT_SHOPPING_CART_ID = 1L;
    private static final Long UPDATED_SHOPPING_CART_ID = 2L;

    private static final String DEFAULT_CART_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_CART_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_SAVE_FOR_LATER = "AAAAAAAAAA";
    private static final String UPDATED_SAVE_FOR_LATER = "BBBBBBBBBB";

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private ShoppingCartMapper shoppingCartMapper;

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private ShoppingCartSearchRepository shoppingCartSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restShoppingCartMockMvc;

    private ShoppingCart shoppingCart;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ShoppingCartResource shoppingCartResource = new ShoppingCartResource(shoppingCartService);
        this.restShoppingCartMockMvc = MockMvcBuilders.standaloneSetup(shoppingCartResource)
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
    public static ShoppingCart createEntity(EntityManager em) {
        ShoppingCart shoppingCart = new ShoppingCart()
            .shoppingCartId(DEFAULT_SHOPPING_CART_ID)
            .cartStatus(DEFAULT_CART_STATUS)
            .saveForLater(DEFAULT_SAVE_FOR_LATER);
        return shoppingCart;
    }

    @Before
    public void initTest() {
        shoppingCartSearchRepository.deleteAll();
        shoppingCart = createEntity(em);
    }

    @Test
    @Transactional
    public void createShoppingCart() throws Exception {
        int databaseSizeBeforeCreate = shoppingCartRepository.findAll().size();

        // Create the ShoppingCart
        ShoppingCartDTO shoppingCartDTO = shoppingCartMapper.toDto(shoppingCart);
        restShoppingCartMockMvc.perform(post("/api/shopping-carts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shoppingCartDTO)))
            .andExpect(status().isCreated());

        // Validate the ShoppingCart in the database
        List<ShoppingCart> shoppingCartList = shoppingCartRepository.findAll();
        assertThat(shoppingCartList).hasSize(databaseSizeBeforeCreate + 1);
        ShoppingCart testShoppingCart = shoppingCartList.get(shoppingCartList.size() - 1);
        assertThat(testShoppingCart.getShoppingCartId()).isEqualTo(DEFAULT_SHOPPING_CART_ID);
        assertThat(testShoppingCart.getCartStatus()).isEqualTo(DEFAULT_CART_STATUS);
        assertThat(testShoppingCart.getSaveForLater()).isEqualTo(DEFAULT_SAVE_FOR_LATER);

        // Validate the ShoppingCart in Elasticsearch
        ShoppingCart shoppingCartEs = shoppingCartSearchRepository.findOne(testShoppingCart.getId());
        assertThat(shoppingCartEs).isEqualToComparingFieldByField(testShoppingCart);
    }

    @Test
    @Transactional
    public void createShoppingCartWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = shoppingCartRepository.findAll().size();

        // Create the ShoppingCart with an existing ID
        shoppingCart.setId(1L);
        ShoppingCartDTO shoppingCartDTO = shoppingCartMapper.toDto(shoppingCart);

        // An entity with an existing ID cannot be created, so this API call must fail
        restShoppingCartMockMvc.perform(post("/api/shopping-carts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shoppingCartDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ShoppingCart> shoppingCartList = shoppingCartRepository.findAll();
        assertThat(shoppingCartList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllShoppingCarts() throws Exception {
        // Initialize the database
        shoppingCartRepository.saveAndFlush(shoppingCart);

        // Get all the shoppingCartList
        restShoppingCartMockMvc.perform(get("/api/shopping-carts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(shoppingCart.getId().intValue())))
            .andExpect(jsonPath("$.[*].shoppingCartId").value(hasItem(DEFAULT_SHOPPING_CART_ID.intValue())))
            .andExpect(jsonPath("$.[*].cartStatus").value(hasItem(DEFAULT_CART_STATUS.toString())))
            .andExpect(jsonPath("$.[*].saveForLater").value(hasItem(DEFAULT_SAVE_FOR_LATER.toString())));
    }

    @Test
    @Transactional
    public void getShoppingCart() throws Exception {
        // Initialize the database
        shoppingCartRepository.saveAndFlush(shoppingCart);

        // Get the shoppingCart
        restShoppingCartMockMvc.perform(get("/api/shopping-carts/{id}", shoppingCart.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(shoppingCart.getId().intValue()))
            .andExpect(jsonPath("$.shoppingCartId").value(DEFAULT_SHOPPING_CART_ID.intValue()))
            .andExpect(jsonPath("$.cartStatus").value(DEFAULT_CART_STATUS.toString()))
            .andExpect(jsonPath("$.saveForLater").value(DEFAULT_SAVE_FOR_LATER.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingShoppingCart() throws Exception {
        // Get the shoppingCart
        restShoppingCartMockMvc.perform(get("/api/shopping-carts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateShoppingCart() throws Exception {
        // Initialize the database
        shoppingCartRepository.saveAndFlush(shoppingCart);
        shoppingCartSearchRepository.save(shoppingCart);
        int databaseSizeBeforeUpdate = shoppingCartRepository.findAll().size();

        // Update the shoppingCart
        ShoppingCart updatedShoppingCart = shoppingCartRepository.findOne(shoppingCart.getId());
        updatedShoppingCart
            .shoppingCartId(UPDATED_SHOPPING_CART_ID)
            .cartStatus(UPDATED_CART_STATUS)
            .saveForLater(UPDATED_SAVE_FOR_LATER);
        ShoppingCartDTO shoppingCartDTO = shoppingCartMapper.toDto(updatedShoppingCart);

        restShoppingCartMockMvc.perform(put("/api/shopping-carts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shoppingCartDTO)))
            .andExpect(status().isOk());

        // Validate the ShoppingCart in the database
        List<ShoppingCart> shoppingCartList = shoppingCartRepository.findAll();
        assertThat(shoppingCartList).hasSize(databaseSizeBeforeUpdate);
        ShoppingCart testShoppingCart = shoppingCartList.get(shoppingCartList.size() - 1);
        assertThat(testShoppingCart.getShoppingCartId()).isEqualTo(UPDATED_SHOPPING_CART_ID);
        assertThat(testShoppingCart.getCartStatus()).isEqualTo(UPDATED_CART_STATUS);
        assertThat(testShoppingCart.getSaveForLater()).isEqualTo(UPDATED_SAVE_FOR_LATER);

        // Validate the ShoppingCart in Elasticsearch
        ShoppingCart shoppingCartEs = shoppingCartSearchRepository.findOne(testShoppingCart.getId());
        assertThat(shoppingCartEs).isEqualToComparingFieldByField(testShoppingCart);
    }

    @Test
    @Transactional
    public void updateNonExistingShoppingCart() throws Exception {
        int databaseSizeBeforeUpdate = shoppingCartRepository.findAll().size();

        // Create the ShoppingCart
        ShoppingCartDTO shoppingCartDTO = shoppingCartMapper.toDto(shoppingCart);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restShoppingCartMockMvc.perform(put("/api/shopping-carts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shoppingCartDTO)))
            .andExpect(status().isCreated());

        // Validate the ShoppingCart in the database
        List<ShoppingCart> shoppingCartList = shoppingCartRepository.findAll();
        assertThat(shoppingCartList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteShoppingCart() throws Exception {
        // Initialize the database
        shoppingCartRepository.saveAndFlush(shoppingCart);
        shoppingCartSearchRepository.save(shoppingCart);
        int databaseSizeBeforeDelete = shoppingCartRepository.findAll().size();

        // Get the shoppingCart
        restShoppingCartMockMvc.perform(delete("/api/shopping-carts/{id}", shoppingCart.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean shoppingCartExistsInEs = shoppingCartSearchRepository.exists(shoppingCart.getId());
        assertThat(shoppingCartExistsInEs).isFalse();

        // Validate the database is empty
        List<ShoppingCart> shoppingCartList = shoppingCartRepository.findAll();
        assertThat(shoppingCartList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchShoppingCart() throws Exception {
        // Initialize the database
        shoppingCartRepository.saveAndFlush(shoppingCart);
        shoppingCartSearchRepository.save(shoppingCart);

        // Search the shoppingCart
        restShoppingCartMockMvc.perform(get("/api/_search/shopping-carts?query=id:" + shoppingCart.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(shoppingCart.getId().intValue())))
            .andExpect(jsonPath("$.[*].shoppingCartId").value(hasItem(DEFAULT_SHOPPING_CART_ID.intValue())))
            .andExpect(jsonPath("$.[*].cartStatus").value(hasItem(DEFAULT_CART_STATUS.toString())))
            .andExpect(jsonPath("$.[*].saveForLater").value(hasItem(DEFAULT_SAVE_FOR_LATER.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ShoppingCart.class);
        ShoppingCart shoppingCart1 = new ShoppingCart();
        shoppingCart1.setId(1L);
        ShoppingCart shoppingCart2 = new ShoppingCart();
        shoppingCart2.setId(shoppingCart1.getId());
        assertThat(shoppingCart1).isEqualTo(shoppingCart2);
        shoppingCart2.setId(2L);
        assertThat(shoppingCart1).isNotEqualTo(shoppingCart2);
        shoppingCart1.setId(null);
        assertThat(shoppingCart1).isNotEqualTo(shoppingCart2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ShoppingCartDTO.class);
        ShoppingCartDTO shoppingCartDTO1 = new ShoppingCartDTO();
        shoppingCartDTO1.setId(1L);
        ShoppingCartDTO shoppingCartDTO2 = new ShoppingCartDTO();
        assertThat(shoppingCartDTO1).isNotEqualTo(shoppingCartDTO2);
        shoppingCartDTO2.setId(shoppingCartDTO1.getId());
        assertThat(shoppingCartDTO1).isEqualTo(shoppingCartDTO2);
        shoppingCartDTO2.setId(2L);
        assertThat(shoppingCartDTO1).isNotEqualTo(shoppingCartDTO2);
        shoppingCartDTO1.setId(null);
        assertThat(shoppingCartDTO1).isNotEqualTo(shoppingCartDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(shoppingCartMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(shoppingCartMapper.fromId(null)).isNull();
    }
}
