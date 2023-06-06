package com.itsync.testproject.service;

import com.itsync.testproject.dto.BooksDTO;
import com.itsync.testproject.dto.FilterDTO;
import com.itsync.testproject.model.Book;
import com.itsync.testproject.model.TypeEnum;
import com.itsync.testproject.repo.BooksRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BooksServiceUnitTest {

    @InjectMocks
    private BooksServiceImpl service;

    @Mock
    private BooksRepository repo;

    @Test
    void createBook() {
        Book book = getBook();
        when(repo.save(any(Book.class))).thenReturn(book);
        Book result = service.createBook(getDTO());
        Assertions.assertEquals(book.getAuthor(), result.getAuthor());
        Assertions.assertEquals(book.getGenre(), result.getGenre());
        Assertions.assertEquals(1, result.getId());
    }

    @Test
    void updateBook_Success() throws Exception {
        Book book = getBook();
        when(repo.findById(anyInt())).thenReturn(Optional.of(book));
        when(repo.save(any(Book.class))).thenReturn(book);
        Book result = service.updateBook(1, getDTO());
        Assertions.assertEquals(book.getAuthor(), result.getAuthor());
        Assertions.assertEquals(book.getGenre(), result.getGenre());
        Assertions.assertEquals(1, result.getId());
    }

    @Test
    void updateBook_Exception() {
        when(repo.findById(anyInt())).thenReturn(Optional.empty());
        Exception exception = assertThrows(Exception.class, () -> service.updateBook(1, getDTO()));
        assertEquals("Unable to find book for provided id", exception.getMessage());
    }

    @Test
    void delete_True() {
        boolean result = service.delete(1);
        assertTrue(result);
        verify(repo).deleteById(anyInt());
    }


    @Test
    void delete_False() {
        doThrow(RuntimeException.class).when(repo).deleteById(anyInt());
        boolean result = service.delete(1);
        assertFalse(result);
        verify(repo).deleteById(anyInt());
    }

    @Test
    void getBooks_Success() {
        FilterDTO filter1 = FilterDTO.builder().minVolume(0).build();
        BooksServiceImpl spyService = spy(service);
        doReturn(getBookList()).when(spyService).getFilteredList(filter1);
        List<Book> list = spyService.getBooks(filter1);
        assertEquals(2, list.size());
        assertEquals(1, list.get(0).getId());
        assertEquals(5, list.get(1).getId());
    }

    BooksDTO getDTO() {
        return new BooksDTO("bookname", "authorName", "sample_genre",  "description",2, TypeEnum.EBOOK);
    }

    Book getBook() {
        return Book.builder()
                .id(1)
                .name("bookname")
                .author("authorName")
                .genre("sample_genre")
                .volumeCount(2)
                .type(TypeEnum.EBOOK)
                .build();
    }

    List<Book> getBookList() {
        return Arrays.asList(
                new Book(1, "name", "author", "gen", "desc1" ,  1, Timestamp.valueOf(LocalDateTime.now()), TypeEnum.HARDCOVER),
                new Book(5, "name2", "author", "gen2", "desc2", 2, Timestamp.valueOf(LocalDateTime.now()), TypeEnum.EBOOK)
        );
    }
}