package com.spirovanni.blackshields.repository.search;

import com.spirovanni.blackshields.domain.ShoppingCart;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ShoppingCart entity.
 */
public interface ShoppingCartSearchRepository extends ElasticsearchRepository<ShoppingCart, Long> {
}
