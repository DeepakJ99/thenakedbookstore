package com.thenakedbookstore.DAO;

import com.thenakedbookstore.models.Slide;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SlideRepository extends JpaRepository<Slide, Long> {

    List<Slide> findAllByBookId(Long bookId);
}
