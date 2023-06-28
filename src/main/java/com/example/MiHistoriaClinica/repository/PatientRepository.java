package com.example.MiHistoriaClinica.repository;


import com.example.MiHistoriaClinica.model.MedicModel;
import com.example.MiHistoriaClinica.model.MedicineModel;
import com.example.MiHistoriaClinica.model.PatientModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface PatientRepository extends JpaRepository<PatientModel, Long> {

    PatientModel findByDniAndPassword(Long dni, String password);

    PatientModel findByDni(Long dni);

    void deleteByDni(Long dni);

    Optional<PatientModel> findByLinkCode(String linkCode);



    /**
     * En esta consulta, estamos seleccionando los médicos (m) de la entidad PatientModel (p)
     * que están asociados a un paciente específico.
     * Utilizamos la cláusula JOIN para unir las entidades PatientModel y MedicModel a través de la relación existente. Luego, especificamos la condición WHERE para filtrar los resultados por el ID del paciente (p.patientId) que se pasa como parámetro.
     */
    @Query("SELECT m FROM PatientModel p JOIN p.medics m WHERE p.patientId = :patientId")
    List<MedicModel> getMedicsByPatientId(@Param("patientId") Long patientId);

    /**
     * JOIN para unir la entidad PatientModel con la lista medicines y luego filtra los resultados por el ID del paciente
     */
    @Query("SELECT m FROM PatientModel p JOIN p.medicines m WHERE p.patientId = :patientId")
    List<MedicineModel> getMedicinesByPatientId(@Param("patientId") Long id);

    @Query("SELECT m FROM PatientModel p JOIN p.medicines m WHERE p.patientId = :patientId AND m.status = :status")
    List<MedicineModel> getMedicinesByPatientIdAndStatus(@Param("patientId") Long id,
                                                         @Param("status") String status);

    // void assignRole(Long patient_id, Long role_id);
}
