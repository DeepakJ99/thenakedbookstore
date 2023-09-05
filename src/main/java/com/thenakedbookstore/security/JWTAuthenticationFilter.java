package com.thenakedbookstore.security;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.SecurityContextDsl;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final JWTHelper jwtHelper;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String header = request.getHeader("Authorization");
        if(header == null || !header.startsWith("Bearer")){
            System.out.println("No header");
            filterChain.doFilter(request, response);
            return;
        }


        String token = "";
        String userName = "";
        try{
            token = header.substring(7);
            userName = jwtHelper.extractUsername(token);
            System.out.println(userName);

        }catch(IllegalArgumentException e){
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }

        if(!userName.isEmpty() && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
            Boolean validated = jwtHelper.validateToken(token, userDetails);

            if(validated){
                System.out.println("User validated");
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }



        filterChain.doFilter(request,response);
    }
}
