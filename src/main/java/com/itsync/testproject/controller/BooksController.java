package com.itsync.testproject.controller;

import com.itsync.testproject.dto.BooksDTO;
import com.itsync.testproject.dto.FilterDTO;
import com.itsync.testproject.model.Book;
import com.itsync.testproject.service.BooksService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/books-api")
@CrossOrigin
public class BooksController {

    private final BooksService service;

    @Operation(description = "Add books")
    @PostMapping
    ResponseEntity<Book> addBook(@RequestBody @Valid BooksDTO dto){
        Book createdBook = service.createBook(dto);
        return ResponseEntity.ok(createdBook);
    }

    @Operation(description = "update books")
    @PutMapping("/update/{bookId}")
    ResponseEntity<?> updateBook(@PathVariable Integer bookId, @RequestBody @Valid BooksDTO dto){
        try {
            Book updatedBook = service.updateBook(bookId, dto);
            return ResponseEntity.ok(updatedBook);
        } catch (Exception e) {
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @Operation(description = "delete books")
    @DeleteMapping("/delete/{bookId}")
    ResponseEntity<Boolean> deleteBook(@PathVariable int bookId){
        boolean deleted = service.delete(bookId);
        return ResponseEntity.ok(deleted);
    }

    @Operation(description = "fetch books based on input filters")
    @PostMapping("/getBooks")
    ResponseEntity<List<Book>> getBooksFiltered(@RequestBody @Valid FilterDTO dto) {
        List<Book> result = service.getBooks(dto);
        return ResponseEntity.ok(result);
    }
}
