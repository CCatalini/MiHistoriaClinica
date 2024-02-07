package com.example.MiHistoriaClinica.presentation.controller;

import com.example.MiHistoriaClinica.persistence.model.Patient;
import com.example.MiHistoriaClinica.presentation.dto.PatientDTO;
import com.example.MiHistoriaClinica.presentation.dto.PatientLoginDTO;
import com.example.MiHistoriaClinica.presentation.dto.TokenDTO;
import com.example.MiHistoriaClinica.service.implementation.AuthService;
import com.example.MiHistoriaClinica.util.exception.InvalidTokenException;
import com.example.MiHistoriaClinica.util.jwt.JwtGenerator;
import com.example.MiHistoriaClinica.util.jwt.JwtGeneratorImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/patient/register")
    @ResponseBody
    public ResponseEntity<TokenDTO> registerPatient(@RequestBody PatientDTO patientDTO) {
        authService.registerPatient(patientDTO);
        TokenDTO token = authService.generateTokenWithEmail(patientDTO.getEmail(), "PATIENT", false);
        return new ResponseEntity<>(token, HttpStatus.CREATED);
    }

    @GetMapping("/patient/confirm-account")
    public ResponseEntity<String> confirmAccount(@RequestHeader("Authorization") String token) throws InvalidTokenException {
        if (authService.confirmAccount(token)) {
            return ResponseEntity.ok("Account confirmed successfully");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Token");
    }
}