package com.example.MiHistoriaClinica.util.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtGeneratorImpl implements JwtGenerator{

    @Value("${app.jwttoken.message}")
    private String message;

    static final SecretKey KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    @Override
    public Map<String, String> generateToken(String id, String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", id);
        claims.put("role", role);
        String jwt = "";
        jwt = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // token valid for 24 hours
                .signWith(KEY)
                .compact();

        Map<String, String> map = new HashMap<>();
        map.put("token", jwt);
        return map;
    }

    @Override
    public Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }


}
