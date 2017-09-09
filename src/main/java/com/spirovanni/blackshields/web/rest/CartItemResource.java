package com.spirovanni.blackshields.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.spirovanni.blackshields.service.CartItemService;
import com.spirovanni.blackshields.web.rest.util.HeaderUtil;
import com.spirovanni.blackshields.service.dto.CartItemDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing CartItem.
 */
@RestController
@RequestMapping("/api")
public class CartItemResource {

    private final Logger log = LoggerFactory.getLogger(CartItemResource.class);

    private static final String ENTITY_NAME = "cartItem";

    private final CartItemService cartItemService;

    public CartItemResource(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    /**
     * POST  /cart-items : Create a new cartItem.
     *
     * @param cartItemDTO the cartItemDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cartItemDTO, or with status 400 (Bad Request) if the cartItem has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/cart-items")
    @Timed
    public ResponseEntity<CartItemDTO> createCartItem(@RequestBody CartItemDTO cartItemDTO) throws URISyntaxException {
        log.debug("REST request to save CartItem : {}", cartItemDTO);
        if (cartItemDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new cartItem cannot already have an ID")).body(null);
        }
        CartItemDTO result = cartItemService.save(cartItemDTO);
        return ResponseEntity.created(new URI("/api/cart-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cart-items : Updates an existing cartItem.
     *
     * @param cartItemDTO the cartItemDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cartItemDTO,
     * or with status 400 (Bad Request) if the cartItemDTO is not valid,
     * or with status 500 (Internal Server Error) if the cartItemDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/cart-items")
    @Timed
    public ResponseEntity<CartItemDTO> updateCartItem(@RequestBody CartItemDTO cartItemDTO) throws URISyntaxException {
        log.debug("REST request to update CartItem : {}", cartItemDTO);
        if (cartItemDTO.getId() == null) {
            return createCartItem(cartItemDTO);
        }
        CartItemDTO result = cartItemService.save(cartItemDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, cartItemDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cart-items : get all the cartItems.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of cartItems in body
     */
    @GetMapping("/cart-items")
    @Timed
    public List<CartItemDTO> getAllCartItems() {
        log.debug("REST request to get all CartItems");
        return cartItemService.findAll();
        }

    /**
     * GET  /cart-items/:id : get the "id" cartItem.
     *
     * @param id the id of the cartItemDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cartItemDTO, or with status 404 (Not Found)
     */
    @GetMapping("/cart-items/{id}")
    @Timed
    public ResponseEntity<CartItemDTO> getCartItem(@PathVariable Long id) {
        log.debug("REST request to get CartItem : {}", id);
        CartItemDTO cartItemDTO = cartItemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(cartItemDTO));
    }

    /**
     * DELETE  /cart-items/:id : delete the "id" cartItem.
     *
     * @param id the id of the cartItemDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/cart-items/{id}")
    @Timed
    public ResponseEntity<Void> deleteCartItem(@PathVariable Long id) {
        log.debug("REST request to delete CartItem : {}", id);
        cartItemService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/cart-items?query=:query : search for the cartItem corresponding
     * to the query.
     *
     * @param query the query of the cartItem search
     * @return the result of the search
     */
    @GetMapping("/_search/cart-items")
    @Timed
    public List<CartItemDTO> searchCartItems(@RequestParam String query) {
        log.debug("REST request to search CartItems for query {}", query);
        return cartItemService.search(query);
    }

}
