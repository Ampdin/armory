package com.spirovanni.blackshields.repository.search;

import com.spirovanni.blackshields.domain.CartItem;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the CartItem entity.
 */
public interface CartItemSearchRepository extends ElasticsearchRepository<CartItem, Long> {
}
