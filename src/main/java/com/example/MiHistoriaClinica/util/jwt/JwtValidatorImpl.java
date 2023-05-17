package com.example.MiHistoriaClinica.util.jwt;

import io.jsonwebtoken.Claims;

public class JwtValidatorImpl implements JwtValidator{

    private JwtGenerator jwtGenerator;

    public JwtValidatorImpl(JwtGenerator jwtGenerator) {
        this.jwtGenerator = jwtGenerator;
    }

    @Override
    public String validateUser(String token) {

        String role = getRole(token);

        if(role.equals("user")){
            return getId(token);
        }

        throw new RuntimeException("Invalid token");
    }

    @Override
    public String validateMedic(String token) {
        String role = getRole(token);

        if(role.equals("medic")){
            return getId(token);
        }

        throw new RuntimeException("Invalid token");
    }

    @Override
    public String getRole(String token) {
        String jwt = token.substring(7);
        Claims claims = jwtGenerator.getClaims(jwt);

        return claims.get("role").toString();
    }

    @Override
    public String getId(String token) {
        String jwt = token.substring(7);
        Claims claims = jwtGenerator.getClaims(jwt);

        return claims.get("id").toString();
    }
}
