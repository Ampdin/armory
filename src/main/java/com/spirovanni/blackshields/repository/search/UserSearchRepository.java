package com.spirovanni.blackshields.repository.search;

import com.spirovanni.blackshields.domain.User;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the User entity.
 */
public interface UserSearchRepository extends ElasticsearchRepository<User, Long> {
}
