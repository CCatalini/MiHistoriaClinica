package com.example.MiHistoriaClinica.service.interfaces;

import com.example.MiHistoriaClinica.dto.PatientLoginDTO;
import com.example.MiHistoriaClinica.dto.PatientSignupDTO;
import com.example.MiHistoriaClinica.model.MedicModel;
import com.example.MiHistoriaClinica.model.MedicalHistoryModel;
import com.example.MiHistoriaClinica.model.MedicineModel;
import com.example.MiHistoriaClinica.model.PatientModel;

import java.util.List;

public interface PatientService {

    PatientModel createPatient(PatientSignupDTO patient);
    PatientModel loginPatient(PatientLoginDTO patient);

    List<MedicineModel> getMedicinesByPatientId(Long id);

    PatientModel getPatientById(Long id);
    PatientModel getPatientByDni(Long dni);
    List<PatientModel> getAllPatient();
    PatientModel updatePatient(Long id, PatientModel newPatient);
    void deletePatient(Long id);
    void deletePatientByDni(Long dni);
    void deleteAllPatient();


    List<MedicModel> getMedicsByPatientId(Long id);

    MedicalHistoryModel getMedicalHistory(Long id);
}
