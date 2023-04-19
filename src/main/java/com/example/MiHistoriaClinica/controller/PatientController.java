package com.example.MiHistoriaClinica.controller;

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

    @PostMapping("/register") // recibe JSON
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

    @GetMapping("/login")
    public String login(Long dni, String password, Model model) {
        PatientModel patient = patientRepository.findByDniAndPass(dni, password);
        if (patient == null) {
            model.addAttribute("error", "Paciente no encontrado");
            return "error";
        } else {
            model.addAttribute("patient", patient);
            return "redirect:/patientMainPage";
        }
    }


    @GetMapping("/{id}")
    public PatientModel getPatient(@PathVariable Long id) {
        return patientRepository.findById(id).orElse(null);
    }

    @GetMapping("/getAllPatient")
    public ArrayList<PatientModel> getAllPatient(){
        return (ArrayList<PatientModel>) patientRepository.findAll();
    }

    @PutMapping("/{id}")
    public PatientModel updatePatient(@PathVariable Long id, @RequestBody PatientModel newPatient) {
        PatientModel patientModel = patientRepository.findById(id).orElseThrow(()
                            -> new ResourceNotFoundException("User not found"));

        patientModel.setName(newPatient.getName());
        patientModel.setLastname(newPatient.getLastname());
        patientModel.setEmail(newPatient.getEmail());
        patientModel.setPassword(newPatient.getPassword());
        patientModel.setDni(newPatient.getDni());
        patientModel.setBirthdate(newPatient.getBirthdate());

        return patientRepository.save(patientModel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        PatientModel existingUser = patientRepository.findById(id).orElseThrow(()
                                -> new ResourceNotFoundException("Patient not found"));
        patientRepository.delete(existingUser);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public void deleteAllPatient(){
        patientRepository.deleteAll();
    }




}

