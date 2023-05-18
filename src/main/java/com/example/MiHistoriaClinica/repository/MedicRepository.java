package com.example.MiHistoriaClinica.repository;

import com.example.MiHistoriaClinica.model.MedicModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;

public interface MedicRepository extends JpaRepository<MedicModel, Long> {

    MedicModel findByDniAndPassword(Long dni, String password);

    MedicModel findByDni(Long dni);

    ResponseEntity<Void> deleteByDni(Long dni);

    MedicModel findByMatriculaAndPassword(Long matricula, String password);

    // void assignRole(Long medic_id, Long role_id);
}
