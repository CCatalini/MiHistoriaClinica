package com.example.MiHistoriaClinica.controller;

import com.example.MiHistoriaClinica.util.jwt.JwtGenerator;
import com.example.MiHistoriaClinica.util.jwt.JwtGeneratorImpl;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/test")
@CrossOrigin("*")
public class TestController {
    @GetMapping("/test")
    public String test(){

        JwtGenerator jwtGenerator = new JwtGeneratorImpl();

        Map<String, String> map = jwtGenerator.generateToken("1", "admin");
        System.out.println(map.get("token"));

        String role = jwtGenerator.getClaims(map.get("token")).get("role").toString();
        String id = jwtGenerator.getClaims(map.get("token")).get("id").toString();

        System.out.println(id);
        System.out.println(role);
        return "test";
    }
}
