package com.example.MiHistoriaClinica.util.jwt;

import com.example.MiHistoriaClinica.presentation.dto.TokenDTO;
import com.example.MiHistoriaClinica.util.exception.InvalidTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;

import javax.crypto.SecretKey;
import java.util.*;

public class JwtGeneratorImpl implements JwtGenerator{

    @Value("${app.jwttoken.message}")
    private String message;
    private final Set<String> invalidTokens = new HashSet<>();
    static final SecretKey KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);


    @Override
    public TokenDTO generateToken(Long id, String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", String.valueOf(id));
        claims.put("role", role);

        String jwt = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // token válido por 24 horas
                .signWith(KEY)
                .compact();

        return new TokenDTO(jwt);
    }


    @Override
    public Claims getClaims(String token) throws InvalidTokenException {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(KEY)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (JwtException e) {
            throw new InvalidTokenException("Token inválido");
        }
    }

    @Override
    public ResponseEntity<Void> invalidateToken(String token) {
        invalidTokens.add(token);
        return ResponseEntity.ok().build();
    }

    @Override
    public TokenDTO generateTokenWithEmail(String email, String role, boolean emailConfirmed) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", String.valueOf(email));
        claims.put("role", role);
        claims.put("emailConfirmed", emailConfirmed);

        String jwt = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // token válido por 24 horas
                .signWith(KEY)
                .compact();

        return new TokenDTO(jwt);
    }

    @Override
    public String getEmailFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.get("email", String.class);
    }


}
