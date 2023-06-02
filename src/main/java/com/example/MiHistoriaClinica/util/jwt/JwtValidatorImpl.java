package com.example.MiHistoriaClinica.util.jwt;

import com.example.MiHistoriaClinica.exception.InvalidTokenException;
import io.jsonwebtoken.Claims;

public class JwtValidatorImpl implements JwtValidator{

    private JwtGenerator jwtGenerator;

    public JwtValidatorImpl(JwtGenerator jwtGenerator) {
        this.jwtGenerator = jwtGenerator;
    }

    @Override
    public Long validateUser(String token) throws InvalidTokenException {

        String role = getRole(token);

        if(role.equals("user")){
            return getId(token);
        }

        throw new RuntimeException("Invalid token");
    }

    @Override
    public Long validateMedic(String token) throws InvalidTokenException {
        String role = getRole(token);

        if(role.equals("medic")){
            return getId(token);
        }

        throw new RuntimeException("Invalid token");
    }

    @Override
    public String getRole(String token) throws InvalidTokenException {
        String jwt = token.substring(7);
        Claims claims = jwtGenerator.getClaims(jwt);

        return claims.get("role").toString();
    }

    @Override
    public Long getId(String token) throws InvalidTokenException {
        String jwt = token.substring(7);
        Claims claims = jwtGenerator.getClaims(jwt);

        return Long.valueOf(claims.get("id").toString());
    }
}
