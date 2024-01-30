package com.thenakedbookstore.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Entity
@Data
@RequiredArgsConstructor
public class BookSlide {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;

    private int likeCount;

    @OneToMany(mappedBy = "slide", cascade = CascadeType.ALL)
    private List<Comment> comments;

    @ManyToOne
    private Book book;

    // Constructors, getters, setters, etc.

}
