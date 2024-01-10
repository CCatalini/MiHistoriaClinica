package com.example.MiHistoriaClinica.service.interfaces;

import com.example.MiHistoriaClinica.dto.*;
import com.example.MiHistoriaClinica.model.*;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
