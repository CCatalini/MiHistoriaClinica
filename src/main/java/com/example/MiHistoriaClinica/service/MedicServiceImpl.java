package com.example.MiHistoriaClinica.service;

import com.example.MiHistoriaClinica.model.MedicModel;
import com.example.MiHistoriaClinica.model.PatientModel;
import com.example.MiHistoriaClinica.repository.MedicRepository;
import com.example.MiHistoriaClinica.repository.PatientRepository;
import org.springframework.transaction.annotation.Transactional;

public class MedicServiceImpl {

    private final MedicRepository medicRepository;
    private final PatientRepository patientRepository;

    public MedicServiceImpl(MedicRepository medicRepository, PatientRepository patientRepository) {
        this.medicRepository = medicRepository;
        this.patientRepository = patientRepository;
    }

    @Transactional
    public void linkPatient(String linkCode, Long medicId) {
        MedicModel medic = medicRepository.findById(medicId).orElse(null);
        if (medic == null) {
            throw new RuntimeException("No se pudo asociar el paciente. El médico no existe.");
        }
        PatientModel patient = patientRepository.findByLinkCode(linkCode);
        if (patient == null) {
            throw new RuntimeException("No se pudo asociar el paciente. El código de enlace no es válido.");
        }
        medic.getPatients().add(patient);
        medicRepository.save(medic);
    }

}
