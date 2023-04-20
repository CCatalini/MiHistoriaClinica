package com.example.MiHistoriaClinica.controller;


import com.example.MiHistoriaClinica.exception.ResourceNotFoundException;
import com.example.MiHistoriaClinica.model.MedicModel;
import com.example.MiHistoriaClinica.repository.MedicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;


@RestController
@RequestMapping("/medic")
@CrossOrigin("*")
public class MedicController {

   @Autowired  private MedicRepository medicRepository;

    @PostMapping("/signup")
    public MedicModel createMedic(@RequestBody MedicModel doctor) {
        return medicRepository.save(doctor);
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
