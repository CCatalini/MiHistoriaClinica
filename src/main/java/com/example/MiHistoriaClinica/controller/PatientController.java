package com.example.MiHistoriaClinica.controller;

import com.example.MiHistoriaClinica.exception.PatientNotFoundException;
import com.example.MiHistoriaClinica.exception.ResourceNotFoundException;
import com.example.MiHistoriaClinica.model.PatientModel;
import com.example.MiHistoriaClinica.model.Role;
import com.example.MiHistoriaClinica.repository.PatientRepository;
import com.example.MiHistoriaClinica.repository.RoleRepository;
import com.example.MiHistoriaClinica.service.PatientServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/patient")
@CrossOrigin("*")
public class PatientController {

    private final PatientServiceImpl patientService;

    @Autowired
    public PatientController(PatientServiceImpl patientService){
        this.patientService = patientService;
    }

    @PostMapping("/signup")
    public ResponseEntity<PatientModel> createPatient(@RequestBody PatientModel patient) {
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
    public ResponseEntity<PatientModel> loginPatient(@RequestBody PatientModel patient) {
        PatientModel loggedInPatient = patientService.loginPatient(patient);
        return new ResponseEntity<>(loggedInPatient, HttpStatus.OK);
    }

    /**
     * Este método recibe el identificador del paciente como parámetro de la URL y llama al método
     * generateLinkCode() de la capa de servicio para generar el código de enlace correspondiente.
     * Luego, devuelve el código de enlace generado como respuesta al cliente.
     */
    @PostMapping("/{patientId}/generate-link-code")
    public ResponseEntity<String> generateLinkCode(@PathVariable Long patientId) {
        String linkCode = patientService.generateLinkCode(patientId);
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

