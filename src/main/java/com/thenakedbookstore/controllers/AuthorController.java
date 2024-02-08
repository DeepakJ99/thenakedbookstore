package com.thenakedbookstore.controllers;

import com.thenakedbookstore.DTO.BookDTO;
import com.thenakedbookstore.models.Author;
import com.thenakedbookstore.models.Book;
import com.thenakedbookstore.services.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/v1/Author")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping("/{authorId}")
    public ResponseEntity<Author> getAuthorById(@PathVariable Long authorId) {
        Author author = authorService.getAuthorById(authorId);
        System.out.println("Sending all authors");
        return ResponseEntity.ok(author);
    }

    @GetMapping()
    public ResponseEntity<List<Author>> getAllAuthors() {
        System.out.println("Get all authors controller");
        List<Author> authors = authorService.getAllAuthors();
        return ResponseEntity.ok(authors);
    }

    @GetMapping("/{authorId}/books")
    public ResponseEntity<List<Book>> getBooksByAuthorId(@PathVariable Long authorId) {
        List<Book> books = authorService.getBooksByAuthorId(authorId);
        return ResponseEntity.ok(books);
    }

    // Additional methods as needed

    @PostMapping("/addNewBook")
    public ResponseEntity<Author> addNewBook(
            @RequestParam String authorName,
            @RequestBody BookDTO bookDTO) {
        Author author = authorService.getOrSaveNew(authorName, bookDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(author);
    }
}
