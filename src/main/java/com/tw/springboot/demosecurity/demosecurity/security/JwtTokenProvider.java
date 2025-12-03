package com.tw.springboot.demosecurity.demosecurity.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private long validityInMilliseconds;

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // Generate token
    public String createToken(Authentication auth) {
        UserDetails user = (UserDetails) auth.getPrincipal();
        String role = auth.getAuthorities().stream().findFirst().map(GrantedAuthority::getAuthority).orElse("USER");
        Date now = new Date();
        Date expiry = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim("role", role)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(getSigningKey(), SignatureAlgorithm.HS384)
                .compact();
    }

    // Get username from token
    public String getUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // Validate token
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
