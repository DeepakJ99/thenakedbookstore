package com.thenakedbookstore.services;


import com.thenakedbookstore.DAO.AuthorRepository;
import com.thenakedbookstore.DTO.BookDTO;
import com.thenakedbookstore.models.Author;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Data
public class AuthorService {

    private final AuthorRepository authorRepository;
    public Author GetOrSaveNew(String author, BookDTO book) {
        Optional<Author> authorObject = authorRepository.findByName(author);
        if(authorObject.isPresent()){
            addBook(authorObject.get(), book);
            return authorObject.get();
        }
        else{
            return newAuthor(author, book);
        }
    }

    public void addBook(Author author, BookDTO bookDTO){
        author.addBook(Book.builder)
    }

    public Author newAuthor(String authorName, BookDTO bookDTO){
        Author author = new Author(authorName);
        author.AddBook()
    }
}
