package com.thenakedbookstore.services;

import com.thenakedbookstore.DAO.BookRepository;
import com.thenakedbookstore.DTO.BookDTO;
import com.thenakedbookstore.models.Author;
import com.thenakedbookstore.models.Book;
import com.thenakedbookstore.models.Slide;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Data
@RequiredArgsConstructor
public class BookService {

    @Autowired
    private final BookRepository bookRepository;
    @Autowired
    private final SlideService slideService;
    @Autowired
    private final AuthorService authorService;

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

    public Book saveBook(BookDTO bookDTO) throws DataIntegrityViolationException {
        System.out.println("savebook bookservice");
        // Additional logic or validation can be added here
        Set<Author> authors = bookDTO.getAuthors().stream().map(authorService::createAuthor).collect(Collectors.toUnmodifiableSet());
        System.out.println("authors: " + authors);
        Book book =  Book.builder()
                .authors(authors)
                .title(bookDTO.getTitle())
                .build();
        Book savedBook = bookRepository.save(book);
        System.out.println("SAVED Book  " + savedBook);
        List<Slide> slides = slideService.createSlides(savedBook);
        System.out.println("Related slides " + slides);
        savedBook.setSlides(slides);

        return savedBook;
    }

    public void deleteBook(Long bookId) {
        bookRepository.deleteById(bookId);
    }

    public Book getBookFromDTO(BookDTO dto){
        Optional<Book> optBook = bookRepository.findById(dto.getId());
        if(optBook.isEmpty()){
            return null;
        }
        return optBook.get();
    }

    public BookDTO getDTOFromBook(Book book){
        return BookDTO.builder()
                .id(book.getId())
                .title(book.getTitle())
                .authors(book.getAuthors().stream().map(Author::getName).toList())
                .slides(book.getSlides().stream().map(Slide::getId).toList())
                .build();

    }
}
