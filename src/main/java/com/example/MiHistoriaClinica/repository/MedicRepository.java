package com.example.MiHistoriaClinica.repository;

import com.example.MiHistoriaClinica.model.MedicModel;
import com.example.MiHistoriaClinica.model.PatientModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface MedicRepository extends JpaRepository<MedicModel, Long> {

    MedicModel findByDniAndPassword(Long dni, String password);

    MedicModel findByDni(Long dni);

    ResponseEntity<Void> deleteByDni(Long dni);

    MedicModel findByMatriculaAndPassword(Long matricula, String password);


/**
 Se utiliza la cláusula JOIN para unir las entidades PatientModel y MedicModel a través de la relación medics.
 Luego, se filtra por m.medic_id para obtener solo los pacientes asociados a un médico específico identificado por medicId.
 Asegúrate de tener correctamente configurada la tabla de unión patient_medic en tu base de datos y las relaciones en las entidades correspondientes. */
@Query("SELECT p FROM PatientModel p JOIN p.medics m WHERE m.medic_id = :medicId")
List<PatientModel> getPatientsByMedicId(@Param("medicId") Long medicId);


}



/*
@Query("SELECT p FROM PatientModel p JOIN p.medics m WHERE m.medic_id = :medicId")
List<PatientModel> getPatientsByMedicId(@Param("medicId") Long medicId);

 */