package com.CarRentalProject.CarRental.Config;

import com.CarRentalProject.CarRental.Services.UserServices.CustomUserDetailsService;
import com.CarRentalProject.CarRental.Utils.JwtToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

/**
 * Security configuration class for the Car Rental application.
 * [Documentation as before]
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private JwtToken jwtToken;

    /**
     * Defines the PasswordEncoder bean using BCrypt.
     *

     * @return a BCryptPasswordEncoder instance
     */
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
     * [Documentation as before]
     *
     * @param http the {@link HttpSecurity} to modify
     * @return the configured {@link SecurityFilterChain}
     * @throws Exception if an error occurs while configuring the security filter chain
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable()) // Disable CSRF for simplicity during testing
                .cors(cors -> cors.configurationSource(request -> {
                    CorsConfiguration config = new CorsConfiguration();
                    config.addAllowedHeader("*");
                    config.addAllowedMethod("*");

                    // *Option 1: Specify Explicit Origins*
                    config.addAllowedOrigin("http://localhost:5173"); // Replace with actual frontend URL
                    // config.addAllowedOrigin("https://another-frontend.com"); // Add more if needed

                    config.setAllowCredentials(true);
                    return config;
                }))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll() // Allow public access to authentication endpoints
                        .requestMatchers(HttpMethod.GET, "/api/v1/vehicules/**").permitAll() // Allow GET requests for vehicules
                        .requestMatchers("/api/admins/**").hasRole("ADMIN") // Require ADMIN role for admin endpoints
                        .anyRequest().authenticated()) // Require authentication for all other requests
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthFilter(), UsernamePasswordAuthenticationFilter.class);


        return http.build();
    }
}