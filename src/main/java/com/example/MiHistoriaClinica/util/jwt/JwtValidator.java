package com.example.MiHistoriaClinica.util.jwt;

public interface JwtValidator {

    Long validateUser(String token);

    Long validateMedic(String token);

    String getRole(String token);

    Long getId(String token);

}
