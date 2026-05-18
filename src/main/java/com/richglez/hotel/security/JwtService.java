package com.richglez.hotel.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration; // en milisegundos

    // transformar el secreto en un formato criptográfico (Key) para firmar
    private Key getSigninKey() {
        return Keys.hmacShaKeyFor(secret.getBytes()); // (Hashed Message Authentication Code).
    } // HMAC SHA

    // El metodo para generar una carta con un destinatario, informacion y sello/firma
    public String
    generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername()) // 2. Agregamos datos (Payload)
                .setIssuedAt(new Date()) // 3. Fecha de inicio
                .setExpiration(new Date(System.currentTimeMillis() + expiration)) // 4. Expira en ...
                .signWith(getSigninKey(), SignatureAlgorithm.HS256) // 5. FIRMAMOS con nuestra Key
                .compact(); // 6. "Empaquetamos" todo en un String
    }

    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigninKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigninKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration()
                .before(new Date());
    }
}

// Token logistic, create, validate and extract info token
