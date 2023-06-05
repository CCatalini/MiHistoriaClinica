package com.example.MiHistoriaClinica.service.interfaces;

import com.example.MiHistoriaClinica.dto.MedicLoginDTO;
import com.example.MiHistoriaClinica.dto.MedicSignupDTO;
import com.example.MiHistoriaClinica.dto.MedicalHistoryModelDTO;
import com.example.MiHistoriaClinica.model.AnalysisModel;
import com.example.MiHistoriaClinica.model.MedicModel;
import com.example.MiHistoriaClinica.model.MedicalHistoryModel;
import com.example.MiHistoriaClinica.model.MedicineModel;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

public interface MedicService {
    MedicModel createMedic(MedicSignupDTO medic);
    MedicModel loginMedic(MedicLoginDTO medic);

    @Transactional
    MedicalHistoryModel createPatientMedicalHistory(Long medicId, Long patientId, MedicalHistoryModelDTO medicalHistory);

    MedicModel getMedicById(Long id);
    MedicModel getMedicByDni(Long dni);
    ArrayList<MedicModel> getAllMedic();
    MedicModel updateMedic(Long id, MedicModel newMedic);
    ResponseEntity<Void> deleteMedic(Long id);
    ResponseEntity<Void> deleteMedicByDni(Long dni);
    void deleteAllMedic();
    void linkPatient(String linkCode, Long medicId);
    MedicineModel addMedicine(MedicineModel medicine);
    AnalysisModel addAnalysis(AnalysisModel analysis);
    MedicalHistoryModel createMedicalHistory(MedicalHistoryModel history);
}
