package com.CarRentalProject.CarRental.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Disable CSRF for simplicity (not recommended for production)
                .authorizeRequests(requests -> {
                    try {
                        requests
                        .requestMatchers("/api/contrats").permitAll() // Allow access to /api/user/add
                        .anyRequest().authenticated()
                        .and()
                        .formLogin(login -> login.permitAll())
                        .logout(logout -> logout.permitAll());
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                });

        return http.build();
    }
}
