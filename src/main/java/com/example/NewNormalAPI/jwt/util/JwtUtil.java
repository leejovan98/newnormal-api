package com.example.NewNormalAPI.jwt.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@Service
public class JwtUtil {
    private String SECRET_KEY = "secret";

    /**
     * Extracts username from existing token
     *  
     * @param token
     * @return username
     */ 
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extracts the expiration date from the existing token
     * 
     * @param token
     * @return existing token's expiration date
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Resolves claim from user's token
     * 
     * @param <T>
     * @param token
     * @param claimsResolver
     * @return 
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Extracts all claims
     * 
     * @param token
     * @return all claims
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    /**
     * Determines whether JWT token is expired or not
     * 
     * @param token
     * @return true if token is expired and false if token is not expired yet
     */
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Generates JWT token by calling createToken

     * @param userDetails
     * @return generated token
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername());
    }

    /**
     * Creates JWT token
     * 
     * @param claims
     * @param subject
     * @return created JWT token
     */
    private String createToken(Map<String, Object> claims, String subject) {

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                // 10 hours expiration date - change last digit to determine hours
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }

    /**
     * Checks if user exists and JWT token is not expired
     * 
     * @param token
     * @param userDetails
     * @return true if user exists and JWT token not expired, false if otherwise
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    /**
     * Extracts the JWT string from the header
     * 
     * @param request
     * @return extracted JWT token
     */
    public String extractJWTString(HttpServletRequest request) {
        final String authorizationHeader = request.getHeader("Authorization");

        String jwt = null;
        
        if(Objects.nonNull(authorizationHeader)) {
        	jwt = authorizationHeader.substring(7);
        }

        return jwt;
    }
}
