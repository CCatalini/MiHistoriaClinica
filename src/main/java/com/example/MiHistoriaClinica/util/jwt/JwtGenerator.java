package com.example.MiHistoriaClinica.util.jwt;

import com.example.MiHistoriaClinica.dto.TokenDTO;
import io.jsonwebtoken.Claims;

import java.util.Map;

public interface JwtGenerator {

    TokenDTO generateToken(Long id, String role);
    Claims getClaims(String token);
}
