package com.spirovanni.blackshields.repository.search;

import com.spirovanni.blackshields.domain.Region;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Region entity.
 */
public interface RegionSearchRepository extends ElasticsearchRepository<Region, Long> {
}
