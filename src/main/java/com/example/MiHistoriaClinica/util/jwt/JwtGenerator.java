package com.example.MiHistoriaClinica.util.jwt;

import io.jsonwebtoken.Claims;

import java.util.Map;

public interface JwtGenerator {

    Map<String, String> generateToken(String id, String role);
    Claims getClaims(String token);
}
