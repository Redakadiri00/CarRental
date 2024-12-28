package com.CarRentalProject.CarRental.Utils;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.function.Function;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtToken {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    @Value("${jwt.expirationEmail}")
    private long expirationEmail;

    private Key signingKey;

    // Initialize the signing key once after the bean is constructed
    @PostConstruct
    private void initSigningKey() {
        if (secret == null || secret.trim().isEmpty()) {
            throw new IllegalArgumentException("JWT secret cannot be null or empty");
        }

        if (secret.length() < 64) { // Ensure the secret is long enough
            throw new IllegalArgumentException("JWT secret must be at least 64 characters for HS512 algorithm");
        }

        signingKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Generates a JWT token for the given username.
     *
     * @param username the username for which the token is generated
     * @return the generated JWT token
     */
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration)) // e.g., 24 hour
                .signWith(signingKey, SignatureAlgorithm.HS512)
                .compact();
    }

    /**
     * Generates a JWT token for email verification.
     *
     * @param email the email to verify
     * @return the generated JWT token
     */
    public String generateEmailVerificationToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .claim("isEmailVerification", true)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationEmail)) // e.g., 1 hours
                .signWith(signingKey, SignatureAlgorithm.HS512)
                .compact();
    }    

    /**
     * Extracts the username (subject) from the token.
     *
     * @param token the JWT token
     * @return the username
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extracts the expiration date from the token.
     *
     * @param token the JWT token
     * @return the expiration date
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Extracts a specific claim from the token.
     *
     * @param token          the JWT token
     * @param claimsResolver a function to resolve the specific claim
     * @param <T>            the type of the claim
     * @return the resolved claim
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Extracts all claims from the token.
     *
     * @param token the JWT token
     * @return the claims
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(signingKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Validates if the token is valid for the given username and not expired.
     *
     * @param token    the JWT token
     * @param username the username to validate against
     * @return true if valid, false otherwise
     */
    public boolean isTokenValid(String token, String username) {
        final String extractedUsername = extractUsername(token);
        return extractedUsername.equals(username) && !isTokenExpired(token);
    }

    /**
     * Checks if the token is expired.
     *
     * @param token the JWT token
     * @return true if expired, false otherwise
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
}
