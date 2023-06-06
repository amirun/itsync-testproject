package com.itsync.testproject.repo;

import com.itsync.testproject.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BooksRepository extends JpaRepository<Book, Integer> {
}
