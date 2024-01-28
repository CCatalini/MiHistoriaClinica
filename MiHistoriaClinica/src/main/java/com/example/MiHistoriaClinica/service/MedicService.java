package com.example.MiHistoriaClinica.service;


import com.example.MiHistoriaClinica.persistence.model.Medic;
import com.example.MiHistoriaClinica.persistence.model.MedicalFile;
import com.example.MiHistoriaClinica.persistence.model.Medicine;
import com.example.MiHistoriaClinica.persistence.model.Patient;
import com.example.MiHistoriaClinica.presentation.dto.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface MedicService {
    Medic createMedic(MedicDTO medic);
    Medic loginMedic(MedicLoginDTO medic);

    List<Medic> getAllMedics();

    void linkPatient(String linkCode, Long medicId);
    void savePatient(Patient patient);

    Optional<Patient> getPatientByLinkCode(String patientLinkCode);
    List<Patient> getPatientsByMedicId(Long medicId);
    List<PatientDTO> getPatientsDtoByMedicId(Long medicId);

    MedicDTO getMedicInfo(Long medicId);
    PatientDTO getPatientInfo(String patientLinkcode);

    void deletePatientLinkCode(String patientLinkCode);

    @Transactional
    MedicalFile createPatientMedicalHistory(Long medicId, String linkCode, MedicalFileDTO medicalHistory);
    MedicalFileDTO getPatientMedicalHistory(String linkCode);

    Medicine createPatientMedicine(Long medicId, String patientLinkCode, MedicineDTO medicine);
    List<Medicine> getMedicinesByPatientLinkCode(String patientLinkCode);
    void deletePatientMedicine(String patientLinkCode, Long medicineId);

    Medic getMedicById(Long id);

    Medic updateMedic(Long id, Medic newMedic);


    List<String> getAllSpecialties();

    List<Medic> getMedicsBySpecialty(String specialty);
}
