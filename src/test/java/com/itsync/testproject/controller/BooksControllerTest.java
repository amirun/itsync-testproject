package com.itsync.testproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itsync.testproject.dto.BooksDTO;
import com.itsync.testproject.dto.FilterDTO;
import com.itsync.testproject.model.Book;
import com.itsync.testproject.model.TypeEnum;
import com.itsync.testproject.service.BooksService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class BooksControllerUnitTest {

    private final String PATH = "/books-api";

    @InjectMocks
    private BooksController controller;

    @Mock
    private BooksService service;

    private MockMvc mockMvc;

    private ObjectMapper mapper;

    @BeforeEach
    public void setup(){
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        mapper = new ObjectMapper();
    }

    @Test
    void addBook_Success() throws Exception {
        when(service.createBook(any(BooksDTO.class))).thenReturn(getBook());

        this.mockMvc.perform(post(PATH)
                .content(mapper.writeValueAsString(getDTO()))
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("bookname"))
                .andExpect(jsonPath("$.author").value("authorName"))
                .andExpect(jsonPath("$.id").value("1"))
                .andDo(print());
    }

    @Test
    void addBook_BadRequest() throws Exception {

        this.mockMvc.perform(post(PATH)
                        .content(mapper.writeValueAsString(
                                new BooksDTO("", "authorName", "sample_genre", "",2, TypeEnum.EBOOK)
                        ))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    void updateBook_Success() throws Exception {
        BooksDTO dto = getDTO();
        when(service.updateBook(1, dto)).thenReturn(getBook());

        this.mockMvc.perform(put(PATH+"/update/1")
                .content(mapper.writeValueAsString(dto))
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("bookname"))
                .andExpect(jsonPath("$.author").value("authorName"))
                .andExpect(jsonPath("$.id").value("1"))
                .andDo(print());
    }

    @Test
    void updateBook_Fail() throws Exception {
        when(service.updateBook(anyInt(), any(BooksDTO.class)))
                .thenThrow(new Exception("Unable to find book for provided id"));

        this.mockMvc.perform(put(PATH+"/update/1")
                        .content(mapper.writeValueAsString(getDTO()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Unable to find book for provided id"))
                .andDo(print());
    }

    @Test
    void deleteBook_Success() throws Exception {
        when(service.delete(anyInt()))
                .thenReturn(true);
        this.mockMvc.perform(delete(PATH+"/delete/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"))
                .andDo(print());
    }

    @Test
    void deleteBook_Fail() throws Exception {
        when(service.delete(anyInt()))
                .thenReturn(false);
        this.mockMvc.perform(delete(PATH+"/delete/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("false"))
                .andDo(print());
    }

    @Test
    void getBooksFiltered() throws Exception{
        when(service.getBooks(any(FilterDTO.class)))
                .thenReturn(getBookList());

        mockMvc.perform(post(PATH+"/getBooks")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8)
                .content(mapper.writeValueAsString(new FilterDTO()))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

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