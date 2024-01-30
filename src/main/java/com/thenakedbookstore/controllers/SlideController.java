package com.thenakedbookstore.controllers;


import com.thenakedbookstore.models.BookSlide;
import com.thenakedbookstore.services.SlideService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/slides")
@RequiredArgsConstructor
@Data
public class SlideController {

    private final SlideService slideService;

    @GetMapping("/random")
    public List<BookSlide> getRandomSlides(){
        return slideService.getRandomSlides();
    }
}
