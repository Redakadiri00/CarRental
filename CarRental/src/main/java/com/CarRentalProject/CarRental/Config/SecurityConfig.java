package com.CarRentalProject.CarRental.Config;

import com.CarRentalProject.CarRental.Services.UserServices.CustomUserDetailsService;
import com.CarRentalProject.CarRental.Utils.JwtToken;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Security configuration class for the Car Rental application.
 * This class configures the security settings, including authentication,
 * authorization, and JWT token handling.
 * 
 * <p>It includes the following configurations:
 * <ul>
 *   <li>Custom user details service for loading user-specific data.</li>
 *   <li>JWT token utility for handling JWT operations.</li>
 *   <li>Password encoder bean for encoding passwords using BCrypt.</li>
 *   <li>Authentication manager bean for managing authentication processes.</li>
 *   <li>JWT authentication filter bean for filtering requests based on JWT tokens.</li>
 *   <li>Security filter chain bean for configuring HTTP security settings.</li>
 * </ul>
 * 
 * <p>Security settings include:
 * <ul>
 *   <li>Disabling CSRF protection for simplicity during testing.</li>
 *   <li>Permitting all requests to authentication endpoints under "/api/auth/**".</li>
 *   <li>Requiring authentication for all other requests.</li>
 *   <li>Setting session management to stateless to ensure no session is created or used by Spring Security.</li>
 *   <li>Adding the JWT authentication filter before the UsernamePasswordAuthenticationFilter.</li>
 * </ul>
 * 
 * <p>Note: This configuration is intended for development and testing purposes.
 * In a production environment, additional security measures should be considered.
 * 
 * @see CustomUserDetailsService
 * @see JwtToken
 * @see BCryptPasswordEncoder
 * @see AuthenticationManager
 * @see JwtAuthFilter
 * @see SecurityFilterChain
 */
@Configuration
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private JwtToken jwtToken;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configures and provides an {@link AuthenticationManager} bean.
     * 
     * @param http the {@link HttpSecurity} to configure
     * @param passwordEncoder the {@link PasswordEncoder} to use for encoding passwords
     * @param userDetailsService the {@link UserDetailsService} to load user-specific data
     * @return the configured {@link AuthenticationManager} instance
     * @throws Exception if an error occurs while configuring the {@link AuthenticationManager}
     */
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, PasswordEncoder passwordEncoder,
            UserDetailsService userDetailsService) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder)
                .and()
                .build();
    }

    /**
     * Creates a JwtAuthFilter bean.
     * 
     * @return a new instance of JwtAuthFilter configured with jwtToken and customUserDetailsService.
     */
    @Bean
    public JwtAuthFilter jwtAuthFilter() {
        return new JwtAuthFilter(jwtToken, customUserDetailsService);
    }

    /**
     * Configures the security filter chain for the application.
     *
     * <p>This method sets up the security configuration using Spring Security's {@link HttpSecurity}.
     * It performs the following configurations:
     * <ul>
     *   <li>Disables CSRF protection for simplicity during testing.</li>
     *   <li>Allows public access to authentication endpoints under the path "/api/auth/**".</li>
     *   <li>Requires authentication for all other requests.</li>
     *   <li>Configures the session management to be stateless.</li>
     *   <li>Adds a custom JWT authentication filter before the {@link UsernamePasswordAuthenticationFilter}.</li>
     * </ul>
     *
     * @param http the {@link HttpSecurity} to modify
     * @return the configured {@link SecurityFilterChain}
     * @throws Exception if an error occurs while configuring the security filter chain
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable()) // Disable CSRF for simplicity during testing
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll() // Allow public access to authentication endpoints
                        .anyRequest().authenticated()) // Require authentication for all other requests
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
