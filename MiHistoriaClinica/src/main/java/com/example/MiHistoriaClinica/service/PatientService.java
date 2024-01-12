package com.example.MiHistoriaClinica.service;

import com.example.MiHistoriaClinica.presentation.dto.MedicalHistoryDTO;
import com.example.MiHistoriaClinica.presentation.dto.PatientLoginDTO;
import com.example.MiHistoriaClinica.presentation.dto.PatientDTO;
import com.example.MiHistoriaClinica.persistence.model.MedicModel;
import com.example.MiHistoriaClinica.persistence.model.MedicineModel;
import com.example.MiHistoriaClinica.persistence.model.PatientModel;

import java.util.List;

public interface PatientService {

    PatientModel createPatient(PatientDTO patient);
    PatientModel loginPatient(PatientLoginDTO patient);
    List<MedicineModel> getMedicinesByPatientId(Long id);
    List<MedicModel> getMedicsByPatientId(Long id);
    PatientModel updatePatient(Long id, PatientModel newPatient);
    MedicalHistoryDTO getMedicalHistory(Long id);
    MedicineModel getMedicineByMedicineId(Long medicineId);
    void saveMedicine(MedicineModel medicine);



    PatientModel getPatientById(Long id);
    PatientModel getPatientByDni(Long dni);

    PatientDTO getPatientInfo(Long patientId);
}


/*

    List<PatientModel> getAllPatient();
    void deletePatient(Long id);
    void deletePatientByDni(Long dni);
    void deleteAllPatient();

 */