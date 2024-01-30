package com.thenakedbookstore;

import com.thenakedbookstore.DTO.LoginRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import com.thenakedbookstore.services.AuthenticationService;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class ThenakedbookstoreApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    private AuthenticationService authenticationService;

    @Test
    public void testLogin() {
        // Replace with actual test data
        String username = "m@n.com";
        String password = "AAA";

        // Perform login
        ResponseEntity<?> authentication = authenticationService.login(new LoginRequest(username, password));

        // Assert that the authentication is successful
        assertNotNull(authentication);
        assertEquals(200, authentication.getStatusCode());

        // Optionally, assert additional details or roles if applicable
        // assertEquals("ROLE_USER", authentication.getAuthorities().iterator().next().getAuthority());

        // Verify that the user is now authenticated in the SecurityContextHolder
        assertEquals(authentication, SecurityContextHolder.getContext().getAuthentication());
    }
}
