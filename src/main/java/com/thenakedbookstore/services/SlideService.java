package com.thenakedbookstore.services;

import com.thenakedbookstore.DAO.SlideRepository;
import com.thenakedbookstore.models.BookSlide;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;


@Service
@Data
@RequiredArgsConstructor
public class SlideService {
    private final SlideRepository slideRepository;
    public List<BookSlide> getRandomSlides() {
        List<BookSlide> allSlides = slideRepository.findAll();
        Collections.shuffle(allSlides);

        int numberOfSlidesToReturn = Math.min(10, allSlides.size());
        return allSlides.subList(0, numberOfSlidesToReturn);
    }
}
