package com.example.MiHistoriaClinica.service;

import com.example.MiHistoriaClinica.exception.PatientNotFoundException;
import com.example.MiHistoriaClinica.exception.ResourceNotFoundException;
import com.example.MiHistoriaClinica.model.PatientModel;
import com.example.MiHistoriaClinica.model.Role;
import com.example.MiHistoriaClinica.repository.PatientRepository;
import com.example.MiHistoriaClinica.repository.RoleRepository;
import com.example.MiHistoriaClinica.service.interfaces.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PatientServiceImpl implements PatientService {

    private PatientRepository patientRepository;
    private RoleRepository roleRepository;


    @Autowired
    public PatientServiceImpl(PatientRepository patientRepository, RoleRepository roleRepository) {
        this.patientRepository = patientRepository;
        this.roleRepository = roleRepository;
    }

    /**
     * método para generar el código de enlace y actualizar el registro del paciente con el nuevo código
     * @param patientId
     * @return linkCode
     */
    public String generateLinkCode(Long patientId) {
        PatientModel patient = patientRepository.findById(patientId).orElse(null);
        if (patient == null) {
            throw new RuntimeException("No se pudo generar el código de enlace. El paciente no existe.");
        }
        String linkCode = UUID.randomUUID().toString().substring(0, 4); // Generar un código aleatorio de 4 caracteres
        patient.setLinkCode(linkCode);
        patientRepository.save(patient);
        return linkCode;
    }

    @Override
    public PatientModel createPatient(PatientModel patient) {
        Role role = roleRepository.findByName("PATIENT_ROLE");
        patient.setRole(role);
        return patientRepository.save(patient);
    }

    @Override
    public PatientModel loginPatient(PatientModel patient) {
        PatientModel result = patientRepository.findByDniAndPassword(patient.getDni(), patient.getPassword());
        if (result == null) {
            throw new PatientNotFoundException();
        } else {
            return result;
        }
    }

    @Override
    public PatientModel getPatientById(Long id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente no encontrado"));
    }

    @Override
    public PatientModel getPatientByDni(Long dni) {
        PatientModel patient = patientRepository.findByDni(dni);
        if (patient == null) {
            throw new ResourceNotFoundException("Paciente no encontrado");
        } else {
            return patient;
        }
    }

    @Override
    public List<PatientModel> getAllPatient() {
        return patientRepository.findAll();
    }

    @Override
    public PatientModel updatePatient(Long id, PatientModel newPatient) {
        PatientModel patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found"));

        patient.setName(newPatient.getName());
        patient.setLastname(newPatient.getLastname());
        patient.setEmail(newPatient.getEmail());
        patient.setPassword(newPatient.getPassword());
        patient.setDni(newPatient.getDni());
        patient.setBirthdate(newPatient.getBirthdate());

        return patientRepository.save(patient);
    }

    @Override
    public void deletePatient(Long id) {
        PatientModel patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found"));
        patientRepository.delete(patient);
    }

    @Override
    public void deletePatientByDni(Long dni) {
        patientRepository.deleteByDni(dni);
    }

    @Override
    public void deleteAllPatient() {
        patientRepository.deleteAll();
    }

}