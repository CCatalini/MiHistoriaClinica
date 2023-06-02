package com.example.MiHistoriaClinica.controller;

import com.example.MiHistoriaClinica.dto.PatientLoginDTO;
import com.example.MiHistoriaClinica.dto.PatientSignupDTO;
import com.example.MiHistoriaClinica.dto.TokenDTO;
import com.example.MiHistoriaClinica.exception.InvalidTokenException;
import com.example.MiHistoriaClinica.model.PatientModel;
import com.example.MiHistoriaClinica.service.PatientServiceImpl;
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

    @PostMapping("/signup")
    public ResponseEntity<PatientModel> createPatient(@RequestBody PatientSignupDTO patient) {
        PatientModel createdPatient = patientService.createPatient(patient);
        return new ResponseEntity<>(createdPatient, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<TokenDTO> loginPatient(@RequestBody PatientLoginDTO patient) {
        PatientModel loggedInPatient = patientService.loginPatient(patient);
        TokenDTO token = jwt.generateToken(loggedInPatient.getPatientId(), "PATIENT");
        return new ResponseEntity<>(token, HttpStatus.OK);
    }


    @PostMapping("/generate-link-code")
    public ResponseEntity<String> generateLinkCode(@RequestHeader("Authorization") String token) throws InvalidTokenException {
        String linkCode = patientService.generateLinkCode(jwtValidator.getId(token));
        return ResponseEntity.ok(linkCode);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logoutPatient(@RequestHeader("Authorization") String token) {
        jwt.invalidateToken(token);
        return ResponseEntity.ok("Logout exitoso");
    }




    @GetMapping("/getById/{id}")
    public ResponseEntity<PatientModel> getPatientById(@PathVariable Long id) {
        PatientModel patient = patientService.getPatientById(id);
        return new ResponseEntity<>(patient, HttpStatus.OK);
    }

    @GetMapping("/getByDni/{dni}")
    public ResponseEntity<PatientModel> getPatientByDni(@PathVariable Long dni) {
        PatientModel patient = patientService.getPatientByDni(dni);
        return new ResponseEntity<>(patient, HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<PatientModel>> getAllPatient() {
        List<PatientModel> patients = patientService.getAllPatient();
        return new ResponseEntity<>(patients, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<PatientModel> updatePatient(@PathVariable Long id, @RequestBody PatientModel patient) {
        PatientModel updatedPatient = patientService.updatePatient(id, patient);
        return new ResponseEntity<>(updatedPatient, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/deleteByDni/{dni}")
    public ResponseEntity<Void> deletePatientByDni(@PathVariable Long dni) {
        patientService.deletePatientByDni(dni);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<Void> deleteAllPatient() {
        patientService.deleteAllPatient();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}

