package com.example.MiHistoriaClinica.persistence.repository;


import com.example.MiHistoriaClinica.persistence.model.*;
import com.example.MiHistoriaClinica.util.constant.MedicalCenterE;
import com.example.MiHistoriaClinica.util.constant.MedicalSpecialtyE;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    Patient findByDniAndPassword(Long dni, String password);

    Patient findByDni(Long dni);

    Patient findByEmail(String email);

    Optional<Patient> findByLinkCode(String linkCode);

    @Modifying
    @Query("UPDATE Patient p SET p.emailConfirmed = :emailConfirmed WHERE p.patientId = :patientId")
    void setEmailConfirmed(@Param("patientId") Long patientId, @Param("emailConfirmed") boolean emailConfirmed);




    /**
     * En esta consulta, estamos seleccionando los médicos (m) de la entidad PatientModel (p)
     * que están asociados a un paciente específico.
     * Utilizamos la cláusula JOIN para unir las entidades PatientModel y MedicModel a través de la relación existente. Luego, especificamos la condición WHERE para filtrar los resultados por el ID del paciente (p.patientId) que se pasa como parámetro.
     */
    @Query("SELECT m FROM Patient p JOIN p.medics m WHERE p.patientId = :patientId")
    List<Medic> getMedicsByPatientId(@Param("patientId") Long patientId);

    /**
     * JOIN para unir la entidad PatientModel con la lista medicinas y luego filtra los resultados por el ID del paciente
     */
    @Query("SELECT m FROM Patient p JOIN p.medicines m WHERE p.patientId = :patientId")
    List<Medicine> getMedicinesByPatientId(@Param("patientId") Long id);

    @Query("SELECT m FROM Patient p JOIN p.medicines m WHERE p.patientId = :patientId AND m.status = :status")
    List<Medicine> getMedicinesByPatientIdAndStatus(@Param("patientId") Long id,
                                                    @Param("status") String status);

    @Query("SELECT m FROM Patient p JOIN p.analysis m WHERE p.patientId = :patientId AND m.status = :status")
    List<Analysis> getAnalysisByPatientIdAndStatus(@Param("patientId") Long id,
                                                   @Param("status") String status);

    @Query("SELECT m FROM Patient p JOIN p.medics m WHERE p.patientId = :patientId AND m.specialty = :specialty")
    List<Medic> getMedicsBySpecialty(@Param("patientId") Long id,
                                     @Param("specialty") MedicalSpecialtyE specialty);

    @Query("SELECT m FROM Patient p JOIN p.analysis m WHERE p.patientId = :patientId AND m.medicalCenterE = :medicalCenter")
    List<Analysis> getAnalysisByPatientIdAndMedicalCenter(@Param("patientId") Long patientId,
                                                          @Param("medicalCenter") MedicalCenterE medicalCenter);

    @Query("SELECT m FROM Patient p JOIN p.analysis m WHERE p.patientId = :patientId")
    List<Analysis> getAnalysisByPatientId(@Param("patientId") Long id);

    @Query("SELECT p.medicalFile FROM Patient p WHERE p.patientId = :patientId")
    MedicalFile getMedicalFileByPatientId(@Param("patientId") Long patientId);




    // void assignRole(Long patient_id, Long role_id);
}


