package com.thenakedbookstore.services;


import com.example.newsarchivesystem.Controllers.AuthenticationResponse;
import com.example.newsarchivesystem.Controllers.RegisterRequest;
import com.example.newsarchivesystem.Security.JWTHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserService userService;

    private final JWTHelper jwtHelper;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;
    public AuthenticationResponse register(RegisterRequest request){
        UserDetails u =  User.builder()
                        .username(request.getUsername())
                        .password(passwordEncoder.encode(request.getPassword()))
                        .roles("USER").build();
        userService.AddUser(u);
        System.out.println(u);
        return new AuthenticationResponse(jwtHelper.generateToken(u));
    }

    public AuthenticationResponse authenticate(RegisterRequest request) {
        authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(
                  request.getUsername(),
                  request.getPassword()
          ));
        UserDetails u = userService.GetUser(request.getUsername());
        return AuthenticationResponse.builder().token(jwtHelper.generateToken(u)).build();
    }
}
