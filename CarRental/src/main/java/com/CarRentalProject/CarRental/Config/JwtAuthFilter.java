package com.CarRentalProject.CarRental.Config;

import com.CarRentalProject.CarRental.DTO.Response.UserResponseDTO;
import com.CarRentalProject.CarRental.Services.UserServices.CustomUserDetailsService;
import com.CarRentalProject.CarRental.Utils.JwtToken;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * JwtAuthFilter is a component that extends OncePerRequestFilter to handle JWT-based authentication
 * for incoming HTTP requests. It intercepts requests to extract and validate JWT tokens from the
 * Authorization header, and sets the authentication in the SecurityContext if the token is valid.
 */
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtToken jwtToken;
    private final CustomUserDetailsService userDetailsService;

    /**
     * JwtAuthFilter is a filter that handles JWT authentication.
     * It intercepts incoming requests and validates the JWT token.
     *
     * @param jwtToken the JWT token utility class used for token operations
     * @param userDetailsService the service used to load user-specific data
     */
    public JwtAuthFilter(JwtToken jwtToken, CustomUserDetailsService userDetailsService) {
        this.jwtToken = jwtToken;
        this.userDetailsService = userDetailsService;
    }

    /**
     * Filters incoming HTTP requests to validate JWT tokens and set the authentication context.
     *
     * @param request  the HTTP request
     * @param response the HTTP response
     * @param chain    the filter chain
     * @throws ServletException if an error occurs during the filtering process
     * @throws IOException      if an I/O error occurs during the filtering process
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        token = authHeader.substring(7);

        try {
            username = jwtToken.extractUsername(token);
        } catch (ExpiredJwtException e) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json");
            response.getWriter().write("{\"message\":\"JWT token has expired\",\"isAuthenticated\":false}");
            return;
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if (jwtToken.isTokenValid(token, userDetails.getUsername())) {
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        chain.doFilter(request, response);
    }
}
