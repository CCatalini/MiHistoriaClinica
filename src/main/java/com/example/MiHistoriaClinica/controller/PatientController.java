package com.example.MiHistoriaClinica.controller;

import com.example.MiHistoriaClinica.dto.PatientLoginDTO;
import com.example.MiHistoriaClinica.dto.PatientSignupDTO;
import com.example.MiHistoriaClinica.dto.TokenDTO;
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
    /*
      @param Patient, es enviado el objeto por el front
     * En una solicitud GET, los parámetros se deben enviar en la URL, no en el cuerpo de la solicitud.
     * Password no debería ser visible en la URL
     * Una mejor práctica es enviar los parámetros en la solicitud POST utilizando el cuerpo de la solicitud en lugar de la URL.
     */
    public ResponseEntity<TokenDTO> loginPatient(@RequestBody PatientLoginDTO patient) {

        PatientModel loggedInPatient = patientService.loginPatient(patient);
        TokenDTO token = jwt.generateToken(loggedInPatient.getPatientId(), "PATIENT");
        return new ResponseEntity<>(token, HttpStatus.OK);
    }



    /**
     * Este método recibe el identificador del paciente como parámetro de la URL y llama al método
     * generateLinkCode() de la capa de servicio para generar el código de enlace correspondiente.
     * Luego, devuelve el código de enlace generado como respuesta al cliente.
     */
 /*   @PostMapping("/{patientId}/generate-link-code")
    public ResponseEntity<String> generateLinkCode(@PathVariable Long patientId) {
        String linkCode = patientService.generateLinkCode(patientId);
        return ResponseEntity.ok(linkCode);
    }*/
    //todo Cami volar patientId y pasar token como header en la request, usando JwtValidator.getId(token) nos devuelve el patientId
    @PostMapping("/generate-link-code")
    public ResponseEntity<String> generateLinkCode(@RequestHeader("Authorization") String token) {
        String linkCode = patientService.generateLinkCode(jwtValidator.getId(token));
        return ResponseEntity.ok(linkCode);
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

