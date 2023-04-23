package com.example.MiHistoriaClinica.controller;

import com.example.MiHistoriaClinica.exception.PatientNotFoundException;
import com.example.MiHistoriaClinica.exception.ResourceNotFoundException;
import com.example.MiHistoriaClinica.model.PatientModel;
import com.example.MiHistoriaClinica.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;

@RestController
@RequestMapping("/patient")
@CrossOrigin("*")
public class PatientController {

    @Autowired private PatientRepository patientRepository;

    @PostMapping("/signup") // recibe JSON
    public PatientModel createPatient(@RequestBody PatientModel user) {
        return patientRepository.save(user);
    }

    @PostMapping() // recibe form
    public String registerUser(@RequestParam String name, @RequestParam String lastname, @RequestParam Long dni,
                               @RequestParam String email, @RequestParam String password, @RequestParam(required = false) Date birthdate) {
        PatientModel patient = new PatientModel();

        patient.setName(name);
        patient.setLastname(lastname);
        patient.setDni(dni);
        patient.setEmail(email);
        patient.setPassword(password);
        patient.setBirthdate(birthdate);

        patientRepository.save(patient);

        return "redirect:/login";
    }

    @PostMapping("/login")
    @ResponseBody
    public PatientModel loginPatient(@RequestBody PatientModel patient) {
        PatientModel result = patientRepository.findByDniAndPassword(patient.getDni(), patient.getPassword());
        if (result == null) {
            throw new PatientNotFoundException();
        } else {
            return result;
        }
    }


    @GetMapping("/getById/{id}")
    public PatientModel getPatientById(@PathVariable Long id) {
        return patientRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException("Paciente no encontrado"));
    }

    @GetMapping("/getByDni/{dni}")
    public Object getPatientByDni(@PathVariable Long dni){
        PatientModel patient = patientRepository.findByDni(dni);
        if(patient == null) return new ResourceNotFoundException("Paciente no encontrado");
        else                return patient;
    }

    // todo probar en postman desde aca y el resto de los controladores
    @GetMapping("/getAll")
    public ArrayList<PatientModel> getAllPatient(){
        return (ArrayList<PatientModel>) patientRepository.findAll();
    }

    @PutMapping("/update/{id}")
    public PatientModel updatePatient(@PathVariable Long id, @RequestBody PatientModel newPatient) {
        PatientModel patient = patientRepository.findById(id).orElseThrow(()
                            -> new ResourceNotFoundException("Patient not found"));

        patient.setName(newPatient.getName());
        patient.setLastname(newPatient.getLastname());
        patient.setEmail(newPatient.getEmail());
        patient.setPassword(newPatient.getPassword());
        patient.setDni(newPatient.getDni());
        patient.setBirthdate(newPatient.getBirthdate());

        return patientRepository.save(patient);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        PatientModel patient = patientRepository.findById(id).orElseThrow(()
                                -> new ResourceNotFoundException("Patient not found"));
        patientRepository.delete(patient);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/deleteByDni/{dni}")
    public void deletePatientByDni (@PathVariable Long dni){
        patientRepository.deleteByDni(dni);
    }

    @DeleteMapping("/deleteAll")
    public void deleteAllPatient(){
        patientRepository.deleteAll();
    }

}

