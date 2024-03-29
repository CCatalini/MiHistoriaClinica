package com.example.MiHistoriaClinica.presentation.controller;

import com.example.MiHistoriaClinica.service.implementation.EmailService;
import com.example.MiHistoriaClinica.util.exception.InvalidTokenException;
import com.example.MiHistoriaClinica.persistence.model.Medic;
import com.example.MiHistoriaClinica.persistence.model.Patient;
import com.example.MiHistoriaClinica.presentation.dto.*;
import com.example.MiHistoriaClinica.service.implementation.PatientServiceImpl;
import com.example.MiHistoriaClinica.util.jwt.JwtGenerator;
import com.example.MiHistoriaClinica.util.jwt.JwtGeneratorImpl;
import com.example.MiHistoriaClinica.util.jwt.JwtValidator;
import com.example.MiHistoriaClinica.util.jwt.JwtValidatorImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patient")
@CrossOrigin("*")
public class PatientController {

    private final PatientServiceImpl patientService;
    private final JwtGenerator jwt = new JwtGeneratorImpl();
    private final JwtValidator jwtValidator = new JwtValidatorImpl(jwt);

    @Autowired
    public PatientController(PatientServiceImpl patientService){
        this.patientService = patientService;
    }


    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<TokenDTO> loginPatient(@RequestBody PatientLoginDTO patient) {
        Patient loggedInPatient = patientService.loginPatient(patient);
        TokenDTO token = jwt.generateToken(loggedInPatient.getPatientId(), "PATIENT");
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logoutPatient(@RequestHeader("Authorization") String token) {
        return jwt.invalidateToken(token);
    }

    @GetMapping("/get-patient-info")
    public ResponseEntity<PatientDTO> getPatientInfo(@RequestHeader("Authorization") String token) throws InvalidTokenException {
        Long patientId = jwtValidator.getId(token);
        PatientDTO patient = patientService.getPatientInfo(patientId);
        return new ResponseEntity<>(patient, HttpStatus.OK);
    }

    @PostMapping("/generate-link-code")
    @ResponseBody
    public ResponseEntity<String> generateLinkCode(@RequestHeader("Authorization") String token) throws InvalidTokenException {
        String linkCode = patientService.generateLinkCode(jwtValidator.getId(token));
        return ResponseEntity.ok(linkCode);
    }

    @GetMapping("/get-medics")
    public ResponseEntity<List<Medic>> getMedics(@RequestHeader("Authorization") String token ) throws InvalidTokenException {
        List<Medic> medics = patientService.getMedicsByPatientId(jwtValidator.getId(token));
        return new ResponseEntity<>(medics, HttpStatus.OK);
    }

    @GetMapping("/get-medics-by-specialty")
    public ResponseEntity<List<Medic>> getMedicsBySpecialty(@RequestHeader("Authorization") String token, @RequestParam("specialty") String specialty) throws InvalidTokenException {
        List<Medic> medics = patientService.getMedicsBySpecialty(jwtValidator.getId(token), specialty);
        return new ResponseEntity<>(medics, HttpStatus.OK);
    }

}
