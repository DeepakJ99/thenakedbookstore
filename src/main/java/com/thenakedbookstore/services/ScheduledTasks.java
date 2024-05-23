package com.thenakedbookstore.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

    @Autowired
    SlideService slideService;
    @Scheduled(fixedRate = 30000)
    public void createSlides(){
        System.out.println("Synced every 30 second");
    }
}
