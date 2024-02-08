package com.thenakedbookstore.DTO;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class BookDTO {

    private Long id;

     String title;

    List<String> authors;

    List<Long> slides;


}
