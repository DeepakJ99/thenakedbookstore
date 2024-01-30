package com.thenakedbookstore.DTO;

import com.thenakedbookstore.models.Author;
import com.thenakedbookstore.models.Book;
import com.thenakedbookstore.models.BookSlide;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@Builder
public class BookDTO {

    private Long id;

    private String title;

    List<String> authors;

    List<Long> slides;

    public Book convert(){

    }
}
