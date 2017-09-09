package com.spirovanni.blackshields.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.spirovanni.blackshields.service.ShoppingCartService;
import com.spirovanni.blackshields.web.rest.util.HeaderUtil;
import com.spirovanni.blackshields.service.dto.ShoppingCartDTO;
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
 * REST controller for managing ShoppingCart.
 */
@RestController
@RequestMapping("/api")
public class ShoppingCartResource {

    private final Logger log = LoggerFactory.getLogger(ShoppingCartResource.class);

    private static final String ENTITY_NAME = "shoppingCart";

    private final ShoppingCartService shoppingCartService;

    public ShoppingCartResource(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    /**
     * POST  /shopping-carts : Create a new shoppingCart.
     *
     * @param shoppingCartDTO the shoppingCartDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new shoppingCartDTO, or with status 400 (Bad Request) if the shoppingCart has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/shopping-carts")
    @Timed
    public ResponseEntity<ShoppingCartDTO> createShoppingCart(@RequestBody ShoppingCartDTO shoppingCartDTO) throws URISyntaxException {
        log.debug("REST request to save ShoppingCart : {}", shoppingCartDTO);
        if (shoppingCartDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new shoppingCart cannot already have an ID")).body(null);
        }
        ShoppingCartDTO result = shoppingCartService.save(shoppingCartDTO);
        return ResponseEntity.created(new URI("/api/shopping-carts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /shopping-carts : Updates an existing shoppingCart.
     *
     * @param shoppingCartDTO the shoppingCartDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated shoppingCartDTO,
     * or with status 400 (Bad Request) if the shoppingCartDTO is not valid,
     * or with status 500 (Internal Server Error) if the shoppingCartDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/shopping-carts")
    @Timed
    public ResponseEntity<ShoppingCartDTO> updateShoppingCart(@RequestBody ShoppingCartDTO shoppingCartDTO) throws URISyntaxException {
        log.debug("REST request to update ShoppingCart : {}", shoppingCartDTO);
        if (shoppingCartDTO.getId() == null) {
            return createShoppingCart(shoppingCartDTO);
        }
        ShoppingCartDTO result = shoppingCartService.save(shoppingCartDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, shoppingCartDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /shopping-carts : get all the shoppingCarts.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of shoppingCarts in body
     */
    @GetMapping("/shopping-carts")
    @Timed
    public List<ShoppingCartDTO> getAllShoppingCarts() {
        log.debug("REST request to get all ShoppingCarts");
        return shoppingCartService.findAll();
        }

    /**
     * GET  /shopping-carts/:id : get the "id" shoppingCart.
     *
     * @param id the id of the shoppingCartDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the shoppingCartDTO, or with status 404 (Not Found)
     */
    @GetMapping("/shopping-carts/{id}")
    @Timed
    public ResponseEntity<ShoppingCartDTO> getShoppingCart(@PathVariable Long id) {
        log.debug("REST request to get ShoppingCart : {}", id);
        ShoppingCartDTO shoppingCartDTO = shoppingCartService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(shoppingCartDTO));
    }

    /**
     * DELETE  /shopping-carts/:id : delete the "id" shoppingCart.
     *
     * @param id the id of the shoppingCartDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/shopping-carts/{id}")
    @Timed
    public ResponseEntity<Void> deleteShoppingCart(@PathVariable Long id) {
        log.debug("REST request to delete ShoppingCart : {}", id);
        shoppingCartService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/shopping-carts?query=:query : search for the shoppingCart corresponding
     * to the query.
     *
     * @param query the query of the shoppingCart search
     * @return the result of the search
     */
    @GetMapping("/_search/shopping-carts")
    @Timed
    public List<ShoppingCartDTO> searchShoppingCarts(@RequestParam String query) {
        log.debug("REST request to search ShoppingCarts for query {}", query);
        return shoppingCartService.search(query);
    }

}
