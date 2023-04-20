package com.example.MiHistoriaClinica.repository;

import com.example.MiHistoriaClinica.model.LabModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LabRepository extends JpaRepository<LabModel, Long> {
}
