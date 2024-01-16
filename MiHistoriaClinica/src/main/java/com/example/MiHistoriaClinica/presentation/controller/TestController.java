package com.example.MiHistoriaClinica.presentation.controller;

import com.example.MiHistoriaClinica.presentation.dto.TokenDTO;
import com.example.MiHistoriaClinica.util.exception.InvalidTokenException;
import com.example.MiHistoriaClinica.util.jwt.JwtGenerator;
import com.example.MiHistoriaClinica.util.jwt.JwtGeneratorImpl;
import com.example.MiHistoriaClinica.util.jwt.JwtValidator;
import com.example.MiHistoriaClinica.util.jwt.JwtValidatorImpl;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
@CrossOrigin("*")
public class TestController {

    @GetMapping("/test")
    public TokenDTO test() throws InvalidTokenException {

        JwtGenerator jwtGenerator = new JwtGeneratorImpl();
        JwtValidator validator = new JwtValidatorImpl(jwtGenerator);

        TokenDTO token = jwtGenerator.generateToken(1L, "medic");
        System.out.println(token.getToken());

        String role = validator.getRole(token.getToken());
        Long id = validator.getId(token.getToken());

        System.out.println(id);
        System.out.println(role);
        return token;
    }

    @GetMapping("/test2")
    public Long test2(@RequestHeader("Authorization") String token) throws RuntimeException, InvalidTokenException {
        JwtValidator validator = new JwtValidatorImpl(new JwtGeneratorImpl());
        Long id = validator.validateMedic(token); //Tira error (Deberia pq uso el token de un user en el test de postman)
        return id;
    }
}
