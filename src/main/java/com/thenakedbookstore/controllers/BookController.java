package com.thenakedbookstore.controllers;


import com.thenakedbookstore.DTO.BookDTO;
import com.thenakedbookstore.models.Author;
import com.thenakedbookstore.models.Book;
import com.thenakedbookstore.models.BookSlide;
import com.thenakedbookstore.services.AuthorService;
import com.thenakedbookstore.services.BookService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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

        return bookService.getAllBooks().stream().map((book) -> {
            return BookDTO.builder()
                    .id(book.getId())
                    .title(book.getTitle())
                    .authors(book.getAuthors().stream().map(Author::getName).toList())
                    .slides(book.getSlides().stream().map(BookSlide::getId).toList())
                    .build();
        }).collect(Collectors.toList());
    }

    @GetMapping("/{bookId}")
    public BookDTO getBookById(@PathVariable Long bookId) {
        Book book = bookService.getBookById(bookId);
        return BookDTO.builder()
                .id(book.getId())
                .title(book.getTitle())
                .authors(book.getAuthors().stream().map(Author::getName).toList())
                .slides(book.getSlides().stream().map(BookSlide::getId).toList())
                .build();
    }

    @PostMapping
    public BookDTO saveBook(@RequestBody BookDTO book) {
        return bookService.saveBook(Book.builder()
                .title(book.getTitle())
                .authors(
                        book.getAuthors().stream().map(
                        (author)->authorService.GetOrSaveNew(author, book)
                ).toList())
                .build();
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity<?> deleteBook(@PathVariable Long bookId) {
        bookService.deleteBook(bookId);
        return ResponseEntity.ok().build();
    }
}
