package com.example.MiHistoriaClinica.persistence.repository;

import com.example.MiHistoriaClinica.persistence.model.MedicalFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicalFileRepository extends JpaRepository<MedicalFile, Long> {
}
