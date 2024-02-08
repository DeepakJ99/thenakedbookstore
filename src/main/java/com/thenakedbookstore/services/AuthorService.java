package com.thenakedbookstore.services;


import com.thenakedbookstore.DAO.AuthorRepository;
import com.thenakedbookstore.DTO.BookDTO;
import com.thenakedbookstore.models.Author;
import com.thenakedbookstore.models.Book;
import jakarta.transaction.Transactional;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Data
public class AuthorService {

    private final AuthorRepository authorRepository;
    public Author getOrSaveNew(String authorName, BookDTO bookDTO) {
        Optional<Author> authorObject = authorRepository.findByName(authorName);
        if(authorObject.isPresent()){

            Author existingAuthor =  authorObject.get();
            return  existingAuthor;
        }
        else{
            Author newAuthor = Author.builder()
                    .name(authorName)
                    .books(new ArrayList<>())
                    .build();
            return authorRepository.save(newAuthor);
        }
    }

    @Transactional
    public void addBook(Author a, Book b){
        List<Book> existingBooks = a.getBooks();
        existingBooks.add(b);
        a.setBooks(existingBooks);
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


    public List<Book> getBooksByAuthorId(Long authorId) {
        Author author = getAuthorById(authorId);
        return author.getBooks();
    }
}
