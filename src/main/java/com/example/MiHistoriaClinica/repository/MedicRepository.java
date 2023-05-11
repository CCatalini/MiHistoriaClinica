package com.example.MiHistoriaClinica.repository;

import com.example.MiHistoriaClinica.model.MedicModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicRepository extends JpaRepository<MedicModel, Long> {

    MedicModel findByDniAndPassword(Long dni, String password);

    MedicModel findByDni(Long dni);

    void deleteByDni(Long dni);

    MedicModel findByMatriculaAndPassword(Long matricula, String password);

    // void assignRole(Long medic_id, Long role_id);
}
