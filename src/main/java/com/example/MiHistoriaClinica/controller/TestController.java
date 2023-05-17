package com.example.MiHistoriaClinica.controller;

import com.example.MiHistoriaClinica.dto.TokenDTO;
import com.example.MiHistoriaClinica.util.jwt.JwtGenerator;
import com.example.MiHistoriaClinica.util.jwt.JwtGeneratorImpl;
import com.example.MiHistoriaClinica.util.jwt.JwtValidator;
import com.example.MiHistoriaClinica.util.jwt.JwtValidatorImpl;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/test")
@CrossOrigin("*")
public class TestController {

    @GetMapping("/test")
    public TokenDTO test(){

        JwtGenerator jwtGenerator = new JwtGeneratorImpl();
        JwtValidator validator = new JwtValidatorImpl(jwtGenerator);

        TokenDTO token = jwtGenerator.generateToken("1", "user");
        System.out.println(token.getToken());

        String role = validator.getRole(token.getToken());
        String id = validator.getId(token.getToken());

        System.out.println(id);
        System.out.println(role);
        return token;
    }

    @GetMapping("/test2")
    public String test2(@RequestHeader("Authorization") String token) throws RuntimeException{
        JwtValidator validator = new JwtValidatorImpl(new JwtGeneratorImpl());
        String id = validator.validateMedic(token); //Tira error (Deberia pq uso el token de un user en el test de postman)
        return id;
    }
}
