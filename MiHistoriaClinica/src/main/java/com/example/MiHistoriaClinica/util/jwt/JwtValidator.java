package com.example.MiHistoriaClinica.util.jwt;

import com.example.MiHistoriaClinica.util.exception.InvalidTokenException;

public interface JwtValidator {

    Long validateUser(String token) throws InvalidTokenException;

    Long validateMedic(String token) throws InvalidTokenException;

    String getRole(String token) throws InvalidTokenException;

    Long getId(String token) throws InvalidTokenException;

}
