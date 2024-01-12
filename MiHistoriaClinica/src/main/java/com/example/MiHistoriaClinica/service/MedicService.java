package com.example.MiHistoriaClinica.service;


import com.example.MiHistoriaClinica.persistence.model.MedicModel;
import com.example.MiHistoriaClinica.persistence.model.MedicalHistoryModel;
import com.example.MiHistoriaClinica.persistence.model.MedicineModel;
import com.example.MiHistoriaClinica.persistence.model.PatientModel;
import com.example.MiHistoriaClinica.presentation.dto.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface MedicService {
    MedicModel createMedic(MedicDTO medic);
    MedicModel loginMedic(MedicLoginDTO medic);

    List<MedicModel> getAllMedics();

    void linkPatient(String linkCode, Long medicId);
    void savePatient(PatientModel patient);

    Optional<PatientModel> getPatientByLinkCode(String patientLinkCode);
    List<PatientModel> getPatientsByMedicId(Long medicId);
    List<PatientDTO> getPatientsDtoByMedicId(Long medicId);

    MedicDTO getMedicInfo(Long medicId);
    PatientDTO getPatientInfo(String patientLinkcode);

    void deletePatientLinkCode(String patientLinkCode);

    @Transactional
    MedicalHistoryModel createPatientMedicalHistory(Long medicId, String linkCode, MedicalHistoryDTO medicalHistory);
    MedicalHistoryDTO getPatientMedicalHistory(String linkCode);

    MedicineModel createPatientMedicine(Long medicId, String patientLinkCode, MedicineDTO medicine);
    List<MedicineModel> getMedicinesByPatientLinkCode(String patientLinkCode);
    void deletePatientMedicine(String patientLinkCode, Long medicineId);

    MedicModel getMedicById(Long id);

    MedicModel updateMedic(Long id, MedicModel newMedic);





}
