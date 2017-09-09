package com.spirovanni.blackshields.repository.search;

import com.spirovanni.blackshields.domain.Job;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Job entity.
 */
public interface JobSearchRepository extends ElasticsearchRepository<Job, Long> {
}
