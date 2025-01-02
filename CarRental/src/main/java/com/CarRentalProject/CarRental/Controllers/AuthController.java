package com.CarRentalProject.CarRental.Controllers;

import com.CarRentalProject.CarRental.DTO.RegistryDTO;
import com.CarRentalProject.CarRental.Enums.UserStatus;
import com.CarRentalProject.CarRental.Mappers.ClientMapper;
import com.CarRentalProject.CarRental.Models.UserModels.Client;
import com.CarRentalProject.CarRental.Models.UserModels.User;
import com.CarRentalProject.CarRental.Services.UserServices.ClientService;
import com.CarRentalProject.CarRental.Services.UserServices.UserService;
import com.CarRentalProject.CarRental.Utils.EmailSender;
import com.CarRentalProject.CarRental.Utils.JwtToken;

import io.jsonwebtoken.ExpiredJwtException;

import com.CarRentalProject.CarRental.DTO.Response.LoginResponseDTO;
import com.CarRentalProject.CarRental.DTO.Response.UserResponseDTO;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Value;


/**
 * AuthController handles authentication requests for the CarRental API.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private ClientMapper clientMapper;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtToken jwtToken;

    @Autowired
    private UserService userService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private EmailSender emailService;

    @Value("${app.url}")
    private String url;



    @GetMapping("/me")
    public ResponseEntity<UserResponseDTO> getCurrentUser() {
        // Retrieve the currently authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        // Fetch the user from the database
        User user = userService.getUserByUsername(username);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(UserResponseDTO.builder()
                            .message("User not found")
                            .isAuthenticated(false)
                            .build());
            
        }

        // Map user to a response DTO
        return ResponseEntity.ok().body(UserResponseDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getClass().getSimpleName().toUpperCase())
                .message("Welcome " + user.getName())
                .isAuthenticated(true)
                .build());
    }

    /**
     * Authenticates a user using credentials sent via headers.
     *
     * @param username the username from the "X-Username" header
     * @param password the password from the "X-Password" header
     * @return a JWT token as a String representing the authenticated user
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> authenticateUser(
            @RequestHeader("X-Username") String username,
            @RequestHeader("X-Password") String password) {
        if (username == null || password == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(LoginResponseDTO.builder()
                            .message("Username and password must be provided")
                            .build());
        }

        try {
            User user = userService.getUserByUsername(username);
            if (user == null) {
                user = userService.getUserByEmail(username);
            } 
            if (user == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(LoginResponseDTO.builder()
                        .message("User not found")
                        .build());
            }
            if (user.getStatus() == UserStatus.UNVERIFIED) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(LoginResponseDTO.builder()
                        .message("User is not verified")
                        .build());
            } else if (user.getStatus() != UserStatus.ACTIVE) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(LoginResponseDTO.builder()
                        .message("User is not active")
                        .build());
            }

        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(LoginResponseDTO.builder()
                    .message("User not found")
                    .build());
        }
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password));

        return ResponseEntity.ok().body(LoginResponseDTO.builder()
                .token(jwtToken.generateToken(authentication.getName()))
                .role(userService.getUserByUsername(username).getClass().getSimpleName().toUpperCase())
                .message("Login successful")
                .build());
    }

    /**
     * Handles unauthorized access requests.
     *
     * @return a ResponseEntity with an unauthorized status code
     */
    @GetMapping("/unauthorized")
    public ResponseEntity<String> unauthorized() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Unauthorized access. Please provide valid credentials.");
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegistryDTO clientDTO) {

        if (clientDTO.getUsername() == null || clientDTO.getEmail() == null || clientDTO.getPassword() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username, email and password must be provided.");
        }

        // Create the user object
        Client client = clientMapper.toEntity(clientDTO);
        client.setStatus(UserStatus.UNVERIFIED);

        // Save the user
        try {
            clientService.createClient(client);
            System.err.println("User created");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

        // Generate email verification token
        String verificationToken = jwtToken.generateToken(client.getEmail());

        // Send email with the verification link
        emailService.sendVerificationEmail(client.getEmail(), verificationToken, url);

        return ResponseEntity.ok("Registration successful. Please check your email for verification.");
    }

    
    /**
     * Endpoint to verify the user's email using a token.
     * 
     * @param token the token sent to the user's email for verification
     * @return ResponseEntity with a message indicating the result of the verification process
     * 
     * This method extracts the email from the provided token and checks its validity.
     * If the token is invalid or expired, it returns a BAD_REQUEST response.
     * If the token is valid, it updates the user's status to ACTIVE and saves the user.
     * Finally, it returns an OK response indicating successful email verification.
     */
    @GetMapping("/verify")
    public ResponseEntity<String> verifyEmail(@RequestParam("token") String token) {
        if (token == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token must be provided");
        }
        String email = jwtToken.extractUsername(token);
        try {
            email = jwtToken.extractUsername(token);
        } catch (ExpiredJwtException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token has expired");
        }

        // Check token validity and claim
        if (!jwtToken.isTokenValid(token, email)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid or expired token");
        }

        // Update user status
        try {
            Client client = clientService.getClientByEmail(email);
            client.setStatus(UserStatus.ACTIVE);
            clientService.updateClient(client);
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not found");
        }

        return ResponseEntity.ok("Email verified successfully");
    }

}
