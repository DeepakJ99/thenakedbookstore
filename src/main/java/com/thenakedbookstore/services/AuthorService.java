package com.thenakedbookstore.services;


import com.thenakedbookstore.DAO.AuthorRepository;
import com.thenakedbookstore.DTO.BookDTO;
import com.thenakedbookstore.models.Author;
import com.thenakedbookstore.models.Book;
import jakarta.transaction.Transactional;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Data
public class AuthorService {

    private final AuthorRepository authorRepository;
    @Transactional
    public Author createAuthor(String authorName) {
        Optional<Author> authorObject = authorRepository.findByName(authorName);
        System.out.println("Author exists"+authorObject.isPresent());
        if(authorObject.isPresent()){
            Author author = authorObject.get();
            System.out.println("Author already exists"+author);
            return authorObject.get();
        }
        else{
            Author newAuthor = Author.builder()
                    .name(authorName)
                    .books(new HashSet<>())
                    .build();
            return authorRepository.save(newAuthor);
        }
    }

    @Transactional
    public void addBook(Author a, Book b){
        Set<Book> books = a.getBooks();
        books.add(b);
        a.setBooks(books);
    }



    public Author getAuthorById(Long authorId) {
        return authorRepository.findById(authorId)
                .orElseThrow(() -> new NoSuchElementException("Author not found with ID: " + authorId));
    }

    public List<Author> getAllAuthors() {
        System.out.println("Getting all authors");
        List<Author> ls = authorRepository.findAll();
        return ls;
    }


    public Set<Book> getBooksByAuthorId(Long authorId) {
        Author author = getAuthorById(authorId);
        return author.getBooks();
    }
}
