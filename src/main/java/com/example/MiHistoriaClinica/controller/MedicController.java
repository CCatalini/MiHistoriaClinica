package com.example.MiHistoriaClinica.controller;


import com.example.MiHistoriaClinica.exception.ResourceNotFoundException;
import com.example.MiHistoriaClinica.model.MedicModel;
import com.example.MiHistoriaClinica.repository.MedicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;


@RestController
@RequestMapping("/doctor")
@CrossOrigin("*")
public class MedicController {

   @Autowired  private MedicRepository medicRepository;

    @PostMapping()
    public MedicModel createDoctor(@RequestBody MedicModel doctor) {
        return medicRepository.save(doctor);
    }

    @PostMapping("/register")
    public String registerDoctor(@RequestParam String name, @RequestParam String lastname, @RequestParam Long dni,
                               @RequestParam String email, @RequestParam Long matricula, @RequestParam String specialty, @RequestParam String password) {
        MedicModel doctor = new MedicModel();

        doctor.setName(name);
        doctor.setLastname(lastname);
        doctor.setDni(dni);
        doctor.setEmail(email);
        doctor.setMatricula(matricula);
        doctor.setSpecialty(specialty);
        doctor.setPassword(password);

        medicRepository.save(doctor);

        return "redirect:/login";
    }

    @GetMapping("/{id}")
    public MedicModel getDoctor(@PathVariable Long id) {
        return medicRepository.findById(id).orElse(null);
    }

    @GetMapping("/getAllDoctor")
    public ArrayList<MedicModel> getAllDoctor(){
        return (ArrayList<MedicModel>) medicRepository.findAll();
    }

    @PutMapping("/{id}")
    public MedicModel updateDoctor(@PathVariable Long id, @RequestBody MedicModel newDoctor) {
        MedicModel doctor = medicRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException("User not found"));

        doctor.setName(newDoctor.getName());
        doctor.setLastname(newDoctor.getLastname());
        doctor.setDni(newDoctor.getDni());
        doctor.setEmail(newDoctor.getEmail());
        doctor.setMatricula(newDoctor.getMatricula());
        doctor.setSpecialty(newDoctor.getSpecialty());
        doctor.setPassword(newDoctor.getPassword());

        return medicRepository.save(doctor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        MedicModel existingDoctor = medicRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException("Doctor not found"));
        medicRepository.delete(existingDoctor);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public void deleteAllPatient(){
        medicRepository.deleteAll();
    }




}
