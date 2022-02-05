package com.spring.jpa.repo;

import com.spring.jpa.entity.Book;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BookRepository extends PagingAndSortingRepository<Book, Long> {
}
