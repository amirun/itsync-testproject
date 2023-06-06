package com.itsync.testproject.service;

import com.itsync.testproject.dto.BooksDTO;
import com.itsync.testproject.dto.FilterDTO;
import com.itsync.testproject.model.Book;

import java.util.List;

public interface BooksService {
    Book createBook(BooksDTO dto);

    Book updateBook(Integer bookId, BooksDTO dto) throws Exception;

    boolean delete(Integer bookId);

    List<Book> getBooks(FilterDTO dto);
}
