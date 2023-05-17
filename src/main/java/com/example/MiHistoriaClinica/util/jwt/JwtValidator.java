package com.example.MiHistoriaClinica.util.jwt;

public interface JwtValidator {

    String validateUser(String token);

    String validateMedic(String token);

    String getRole(String token);

    String getId(String token);

}
