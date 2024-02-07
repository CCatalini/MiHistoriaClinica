package com.example.MiHistoriaClinica.util.jwt;

import com.example.MiHistoriaClinica.presentation.dto.TokenDTO;
import com.example.MiHistoriaClinica.util.exception.InvalidTokenException;
import io.jsonwebtoken.Claims;
import org.springframework.http.ResponseEntity;

public interface JwtGenerator {


    TokenDTO generateToken(Long id, String role);

    Claims getClaims(String token) throws InvalidTokenException;

    ResponseEntity<Void> invalidateToken(String token);

    TokenDTO generateTokenWithEmail(String email, String patient, boolean b);

}
