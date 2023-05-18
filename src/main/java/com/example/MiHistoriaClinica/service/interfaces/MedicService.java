package com.example.MiHistoriaClinica.service.interfaces;

import com.example.MiHistoriaClinica.model.AnalysisModel;
import com.example.MiHistoriaClinica.model.MedicModel;
import com.example.MiHistoriaClinica.model.MedicalHistoryModel;
import com.example.MiHistoriaClinica.model.MedicineModel;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;

public interface MedicService {
    MedicModel createMedic(MedicModel medic);
    MedicModel loginMedic(MedicModel medic);
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
