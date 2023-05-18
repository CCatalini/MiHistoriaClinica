package com.example.MiHistoriaClinica.service.interfaces;

import com.example.MiHistoriaClinica.model.PatientModel;

import java.util.List;

public interface PatientService {

    PatientModel createPatient(PatientModel patient);
    PatientModel loginPatient(PatientModel patient);
    PatientModel getPatientById(Long id);
    PatientModel getPatientByDni(Long dni);
    List<PatientModel> getAllPatient();
    PatientModel updatePatient(Long id, PatientModel newPatient);
    void deletePatient(Long id);
    void deletePatientByDni(Long dni);
    void deleteAllPatient();


}
