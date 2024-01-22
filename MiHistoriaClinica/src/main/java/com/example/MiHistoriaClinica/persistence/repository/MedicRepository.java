package com.example.MiHistoriaClinica.persistence.repository;

import com.example.MiHistoriaClinica.persistence.model.Medic;
import com.example.MiHistoriaClinica.persistence.model.Patient;
import com.example.MiHistoriaClinica.util.constant.MedicalSpecialtyE;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface MedicRepository extends JpaRepository<Medic, Long> {

    Medic findByMatriculaAndPassword(Long matricula, String password);

    /**
    Se utiliza la cláusula JOIN para unir las entidades PatientModel y MedicModel a través de la relación medics.
    Luego, se filtra por m.medic_id para obtener solo los pacientes asociados a un médico específico identificado por medicId.
     Asegúrate de tener correctamente configurada la tabla de unión patient_medic en tu base de datos y las relaciones en las entidades correspondientes. */
    @Query("SELECT p FROM Medic m JOIN m.patients p WHERE m.medicId = :medicId")
    List<Patient> getPatientsByMedicId(@Param("medicId") Long medicId);

    @Query("SELECT m FROM Medic m WHERE m.specialty = :specialty")
    List<Medic> getMedicsBySpecialty(MedicalSpecialtyE specialty);


}
