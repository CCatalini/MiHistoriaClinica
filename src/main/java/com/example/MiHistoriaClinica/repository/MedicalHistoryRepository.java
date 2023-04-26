package com.example.MiHistoriaClinica.repository;

import com.example.MiHistoriaClinica.model.MedicalHistoryModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicalHistoryRepository extends JpaRepository<MedicalHistoryModel, Long> {
}
