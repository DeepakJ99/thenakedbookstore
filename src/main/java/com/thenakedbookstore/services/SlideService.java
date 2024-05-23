package com.thenakedbookstore.services;

import com.thenakedbookstore.DAO.SlideRepository;
import com.thenakedbookstore.models.Book;
import com.thenakedbookstore.models.Slide;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
@Data
@RequiredArgsConstructor
public class SlideService {

    int slidesPerBook=5;
    private final SlideRepository slideRepository;
    public List<Slide> getRandomSlides() {
        List<Slide> allSlides = slideRepository.findAll();
        Collections.shuffle(allSlides);

        int numberOfSlidesToReturn = Math.min(10, allSlides.size());
        return allSlides.subList(0, numberOfSlidesToReturn);
    }

    public List<Slide> createSlides(Book book) {
        List<Slide> ls = new ArrayList<>();
        for(int i=0;i<slidesPerBook;i++){
            Slide slide = Slide.builder()
                    .comments(new ArrayList<>())
                    .likeCount(0)
                    .book(book)
                    .text("Place holder text"+new Random().nextDouble())
                    .build();
            ls.add(slide);
            slideRepository.save(slide);
        }
        return slideRepository.findAllByBookId(book.getId());
    }
}
