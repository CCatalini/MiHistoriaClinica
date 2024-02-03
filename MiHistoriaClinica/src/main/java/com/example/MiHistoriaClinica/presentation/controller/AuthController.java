package com.example.MiHistoriaClinica.presentation.controller;

import com.example.MiHistoriaClinica.persistence.model.Patient;
import com.example.MiHistoriaClinica.presentation.dto.PatientDTO;
import com.example.MiHistoriaClinica.service.AuthService;
import com.example.MiHistoriaClinica.service.implementation.EmailService;
import com.example.MiHistoriaClinica.service.implementation.PatientServiceImpl;
import com.example.MiHistoriaClinica.util.exception.InvalidTokenException;
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
    public ResponseEntity<Patient> registerPatient(@RequestBody PatientDTO patientDTO) {
        Patient patientCreated = authService.registerPatient(patientDTO);
        return new ResponseEntity<>(patientCreated, HttpStatus.CREATED);
    }


    @GetMapping("/patient/confirm-account")
    public String confirmAccount(@RequestHeader("Authorization") String authorizationHeader) {
        String token = extractToken(authorizationHeader);
        if (authService.confirmAccount(token)) {
            return "redirect:http://localhost:4200/patient/login";
        }
        return "redirect:http://localhost:4200/";
    }

    private String extractToken(String authorizationHeader) {
        // Ajusta la lógica para extraer el token del encabezado "Authorization"
        // Por ejemplo, puedes hacer un split para obtener el token si está en el formato "Bearer token"
        String[] parts = authorizationHeader.split(" ");
        if (parts.length == 2 && "Bearer".equals(parts[0])) {
            return parts[1];
        }
        return null;
    }



}
