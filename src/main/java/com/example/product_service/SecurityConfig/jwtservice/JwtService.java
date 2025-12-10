package com.example.product_service.SecurityConfig.jwtservice;

import com.example.product_service.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {
    private final String SECRET = "your-very-secret-key";
    private final long EXPIRATION = 1000 * 60 * 60 * 10;

    public String generateToken(User user){
        Map<String,Object> claims = new HashMap<>();
        claims.put("userId",user.getId());
        claims.put("tenantId",user.getTenant().getId());
        claims.put("role",user.getRole().name());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(SignatureAlgorithm.HS256,SECRET)
                .compact();
    }

    public Claims extractAllClaims(String token){
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody();
    }

    public Long extractUserId(String token){
        return extractAllClaims(token).get("userId", Long.class);
    }



}
