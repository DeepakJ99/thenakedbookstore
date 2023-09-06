package com.thenakedbookstore.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/users")
    public String getUsers(){
        return "index";
    }
}
