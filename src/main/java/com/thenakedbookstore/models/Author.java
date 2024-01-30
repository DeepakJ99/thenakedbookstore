package com.thenakedbookstore.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Entity
@Data
@RequiredArgsConstructor
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "authors", cascade = CascadeType.ALL)
    private List<Book> books;

    // Constructors, getters, setters, etc.

    public Author(String name){
        this.name = name;
    }

}