package com.thenakedbookstore.DAO;

import com.thenakedbookstore.models.BookSlide;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SlideRepository extends JpaRepository<BookSlide, Long> {

}
