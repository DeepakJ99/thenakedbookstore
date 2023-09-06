package com.thenakedbookstore.services;


import com.thenakedbookstore.controllers.AuthenticationResponse;
import com.thenakedbookstore.controllers.RegisterRequest;
import com.thenakedbookstore.models.Customer;
import com.thenakedbookstore.models.Role;
import com.thenakedbookstore.security.JWTHelper;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthenticationService {
    private final CustomerService customerService;
    private final JWTHelper jwtHelper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    public AuthenticationResponse register(RegisterRequest request){
        Customer c =  Customer.builder()
                        .email(request.getUsername())
                        .password(passwordEncoder.encode(request.getPassword()))
                        .role(Role.USER).build();

        //returns true if a new customer is created
        if(customerService.createCustomer(c)){
            return AuthenticationResponse.builder().token(jwtHelper.generateToken(c)).build();
        }
        return null;
    }

    public AuthenticationResponse authenticate(RegisterRequest request) {
        authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(
                  request.getUsername(),
                  request.getPassword()
          ));
        Customer c  = customerService.getCustomerById(request.getUsername());
        return AuthenticationResponse.builder().token(jwtHelper.generateToken(c)).build();
    }
}
