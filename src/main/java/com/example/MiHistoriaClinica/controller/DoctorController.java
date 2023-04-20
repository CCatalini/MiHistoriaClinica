package com.example.MiHistoriaClinica.controller;


import com.example.MiHistoriaClinica.exception.ResourceNotFoundException;
import com.example.MiHistoriaClinica.model.DoctorModel;
import com.example.MiHistoriaClinica.model.PatientModel;
import com.example.MiHistoriaClinica.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.print.Doc;
import java.util.ArrayList;


@RestController
@RequestMapping("/doctor")
@CrossOrigin("*")
public class DoctorController {

   @Autowired  private DoctorRepository doctorRepository;

    @PostMapping("/register") //recibe JSON
    public DoctorModel createDoctor(@RequestBody DoctorModel doctor) {
        return doctorRepository.save(doctor);
    }

    @GetMapping("/login")
    public String login(Long dni, String password, Model model) {
        DoctorModel doctor = doctorRepository.findByDniAndPassword(dni, password);
        if (doctor == null) {
            model.addAttribute("error", "Doctor no registrado");
            return "error";
        } else {
            model.addAttribute("doctor", doctor);
            return "redirect:/doctorMainPage";
        }
    }

    @GetMapping("/{id}")
    public DoctorModel getDoctor(@PathVariable Long id) {
        return doctorRepository.findById(id).orElse(null);
    }

    @GetMapping("/getAllDoctor")
    public ArrayList<DoctorModel> getAllDoctor(){
        return (ArrayList<DoctorModel>) doctorRepository.findAll();
    }

    @PutMapping("/{id}")
    public DoctorModel updateDoctor(@PathVariable Long id, @RequestBody DoctorModel newDoctor) {
        DoctorModel doctor = doctorRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException("User not found"));

        doctor.setName(newDoctor.getName());
        doctor.setLastname(newDoctor.getLastname());
        doctor.setDni(newDoctor.getDni());
        doctor.setEmail(newDoctor.getEmail());
        doctor.setMatricula(newDoctor.getMatricula());
        doctor.setSpecialty(newDoctor.getSpecialty());
        doctor.setPassword(newDoctor.getPassword());

        return doctorRepository.save(doctor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        DoctorModel existingDoctor = doctorRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException("Doctor not found"));
        doctorRepository.delete(existingDoctor);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public void deleteAllPatient(){
        doctorRepository.deleteAll();
    }




}
