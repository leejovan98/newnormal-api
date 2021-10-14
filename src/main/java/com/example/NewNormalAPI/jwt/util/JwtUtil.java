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
import java.util.function.Function;

@Service
public class JwtUtil {
    // TODO change secret key
    private String SECRET_KEY = "secret";

    // extracts uesrname from existing token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // extracts the expiration date from the existing token
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // resolves claim from user's token
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // generates token by calling createToken
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername());
    }

    private String createToken(Map<String, Object> claims, String subject) {

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                // 10 hours expiration date - change last digit to determine hours
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }

    // checks if user exists and token is not expired
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    // extracts the jwt string from the header
    public String extractJWTString(HttpServletRequest request) {
        final String authorizationHeader = request.getHeader("Authorization");

        String jwt = null;
        jwt = authorizationHeader.substring(7);
        
        return jwt;
    }
}
