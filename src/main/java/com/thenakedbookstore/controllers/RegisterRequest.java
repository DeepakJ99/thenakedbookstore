package com.thenakedbookstore.controllers;


import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class RegisterRequest {
    public String username;
    public String name;
    public String password;
}
