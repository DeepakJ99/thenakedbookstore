package com.thenakedbookstore.services;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.thenakedbookstore.DAO.TokenRepository;
import com.thenakedbookstore.DAO.UserRepository;
import com.thenakedbookstore.DTO.LoginRequest;
import com.thenakedbookstore.DTO.RegistrationRequest;
import com.thenakedbookstore.config.SecurityConfig;
import com.thenakedbookstore.config.TokenType;

import com.thenakedbookstore.models.Token;
import com.thenakedbookstore.models.User;
import com.thenakedbookstore.security.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@AllArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    @Autowired
    private final AuthenticationManager authenticationManager;


    public ResponseEntity<?> register(RegistrationRequest request) {
        try {
            // Check if a user with the given email already exists
            if (repository.findByEmail(request.getEmail()).isPresent()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("User with this email already exists");
            }

            // If the user doesn't exist, proceed with registration
            var user = User.builder()
                    .name(request.getName())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(request.getRole())
                    .build();

            var savedUser = repository.save(user);


            return ResponseEntity.status(HttpStatus.OK).body("Registration Successful");
        } catch (DataIntegrityViolationException e) {
            // Handle other database-related exceptions if needed
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during user registration");
        }
    }
    public ResponseEntity<?> login(LoginRequest request) {

        System.out.println(request);
        try {

            // Check if a user with the given username exists
            var user = repository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new RuntimeException("User with this username not found."));
            Authentication authResult = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail().strip(),
                            request.getPassword().strip()
                    )
            );
         //    Continue with authentication logic
            var jwtToken = jwtService.generateToken(user);
            var refreshToken = jwtService.generateRefreshToken(user);
            System.out.println(jwtToken);
            revokeAllUserTokens(user);
            saveUserToken(user, jwtToken);

            return ResponseEntity.ok(SecurityConfig.AuthenticationResponse.builder()
                    .accessToken(jwtToken)
                        .refreshToken(refreshToken)
                            .role(user.getRole().toString())
                                .build());
        } catch (RuntimeException e) {
            System.out.println("User not found");
            // Handle the case where the user with the given username is not found
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found or invalid credentials.");
        } catch (Exception e) {
            System.out.println("2");
            // Handle other exceptions if needed
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during login.");
        }
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getID());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null) {
            var user = this.repository.findByEmail(userEmail)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                var authResponse = SecurityConfig.AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }
}
