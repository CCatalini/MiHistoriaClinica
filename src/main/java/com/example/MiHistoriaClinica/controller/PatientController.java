package com.example.MiHistoriaClinica.controller;

import com.example.MiHistoriaClinica.exception.PatientNotFoundException;
import com.example.MiHistoriaClinica.exception.ResourceNotFoundException;
import com.example.MiHistoriaClinica.model.PatientModel;
import com.example.MiHistoriaClinica.model.Role;
import com.example.MiHistoriaClinica.repository.PatientRepository;
import com.example.MiHistoriaClinica.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;

@RestController
@RequestMapping("/patient")
@CrossOrigin("*")
public class PatientController {

    @Autowired private PatientRepository patientRepository;

    @Autowired private RoleRepository roleRepository;

    @PostMapping("/signup") // recibe JSON
    public PatientModel createPatient(@RequestBody PatientModel patient) {

        // Asignar rol por defecto al paciente
        Role role = roleRepository.findByName("PATIENT_ROLE");
        patient.setRole(role);

        return patientRepository.save(patient);
    }

    @PostMapping("/login")
    @ResponseBody
    /*
      @param Patient, es enviado el objeto por el front
     * En una solicitud GET, los parámetros se deben enviar en la URL, no en el cuerpo de la solicitud.
     * Password no debería ser visible en la URL
     * Una mejor práctica es enviar los parámetros en la solicitud POST utilizando el cuerpo de la solicitud en lugar de la URL.
     */
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

