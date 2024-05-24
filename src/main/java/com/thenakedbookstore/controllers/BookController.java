package com.thenakedbookstore.controllers;


import com.thenakedbookstore.DTO.BookDTO;
import com.thenakedbookstore.models.Book;
import com.thenakedbookstore.services.AuthorService;
import com.thenakedbookstore.services.BookService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Data
@RestController
@RequestMapping("/api/v1/book")
@CrossOrigin(origins = "http://localhost:3000")
public class BookController {

    private final BookService bookService;
    private final AuthorService authorService;
    @GetMapping()
    public List<BookDTO> getAllBooks() {

        return bookService.getAllBooks().stream().map(bookService::getDTOFromBook).collect(Collectors.toList());
    }

    @GetMapping("/{bookId}")
    public BookDTO getBookById(@PathVariable Long bookId) {
        Book book = bookService.getBookById(bookId);
        return bookService.getDTOFromBook(book);
    }

    @PostMapping
    public ResponseEntity<?> saveBook(@RequestBody BookDTO book) {
        try{
            return  ResponseEntity.ok().body(bookService.getDTOFromBook(bookService.saveBook(book)));

        }catch (DataIntegrityViolationException d){
            return  ResponseEntity.badRequest().body(d.getMessage());
        }
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity<?> deleteBook(@PathVariable Long bookId) {
        bookService.deleteBook(bookId);
        return ResponseEntity.ok().build();
    }
}
