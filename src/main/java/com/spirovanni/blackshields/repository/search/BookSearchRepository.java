package com.spirovanni.blackshields.repository.search;

import com.spirovanni.blackshields.domain.Book;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Book entity.
 */
public interface BookSearchRepository extends ElasticsearchRepository<Book, Long> {
}
