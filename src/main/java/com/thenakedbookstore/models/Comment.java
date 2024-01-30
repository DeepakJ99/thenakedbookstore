package com.thenakedbookstore.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@RequiredArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;

    private int likeCount;

    @ManyToOne
    private BookSlide slide;

    // Constructors, getters, setters, etc.

}
