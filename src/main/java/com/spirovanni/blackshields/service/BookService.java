package com.spirovanni.blackshields.service;

import com.spirovanni.blackshields.service.dto.BookDTO;
import java.util.List;

/**
 * Service Interface for managing Book.
 */
public interface BookService {

    /**
     * Save a book.
     *
     * @param bookDTO the entity to save
     * @return the persisted entity
     */
    BookDTO save(BookDTO bookDTO);

    /**
     *  Get all the books.
     *
     *  @return the list of entities
     */
    List<BookDTO> findAll();

    /**
     *  Get the "id" book.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    BookDTO findOne(Long id);

    /**
     *  Delete the "id" book.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the book corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<BookDTO> search(String query);
}
