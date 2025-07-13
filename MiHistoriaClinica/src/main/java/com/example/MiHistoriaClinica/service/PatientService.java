package com.example.MiHistoriaClinica.service;

import com.example.MiHistoriaClinica.persistence.model.Turnos;
import com.example.MiHistoriaClinica.presentation.dto.MedicalFileDTO;
import com.example.MiHistoriaClinica.presentation.dto.PatientLoginDTO;
import com.example.MiHistoriaClinica.presentation.dto.PatientDTO;
import com.example.MiHistoriaClinica.persistence.model.Medic;
import com.example.MiHistoriaClinica.persistence.model.Medicine;
import com.example.MiHistoriaClinica.persistence.model.Patient;
import com.example.MiHistoriaClinica.presentation.dto.TurnoDTO;
import com.example.MiHistoriaClinica.presentation.dto.MedicTurnosDTO;

import java.util.List;

public interface PatientService {

    Patient createPatient(PatientDTO patient);
    Patient loginPatient(PatientLoginDTO patient);
    List<Medicine> getMedicinesByPatientId(Long id);
    List<Medic> getMedicsByPatientId(Long id);
    Patient updatePatient(Long id, Patient newPatient);
    MedicalFileDTO getMedicalHistory(Long id);
    Medicine getMedicineByMedicineId(Long medicineId);
    void saveMedicine(Medicine medicine);



    Patient getPatientById(Long id);
    Patient getPatientByDni(Long dni);

    PatientDTO getPatientInfo(Long patientId);

    void reserveTurno(Long patientId, Long turnoId);

    List<Turnos> getMisTurnos(Long id);

    void deleteTurno(Long id);

    List<Medic> getMedicsBySpecialty(Long id, String specialty);

    List<Turnos> getAvailableTurnosByMedic(Long medicId);

    List<MedicTurnosDTO> searchAvailableTurnosBySpecialtyAndDate(String specialty, String dateIso);
}


/*

    List<PatientModel> getAllPatient();
    void deletePatient(Long id);
    void deletePatientByDni(Long dni);
    void deleteAllPatient();

 */