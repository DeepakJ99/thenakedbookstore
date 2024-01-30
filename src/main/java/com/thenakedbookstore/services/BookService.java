package com.thenakedbookstore.services;

import com.thenakedbookstore.DAO.BookRepository;
import com.thenakedbookstore.models.Book;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Data
@RequiredArgsConstructor
public class BookService {

    @Autowired
    private final BookRepository bookRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBookById(Long bookId) {
        try{
            return bookRepository.findById(bookId)
                    .orElseThrow(() -> new Exception("Book not found with id: " + bookId));
        }catch (Exception e){
            return null;
        }

    }

    public Book saveBook(Book book) {
        // Additional logic or validation can be added here
        return bookRepository.save(book);
    }

    public void deleteBook(Long bookId) {
        bookRepository.deleteById(bookId);
    }
}
