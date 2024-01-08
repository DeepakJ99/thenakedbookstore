package com.thenakedbookstore.DTO;

import com.thenakedbookstore.models.Role;
import lombok.*;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class RegistrationRequest {
    public String name;
    public String email;
    public String password;
    public String cpassword;
    public Role role;
}
