package com.example.MiHistoriaClinica.repository;

import com.example.MiHistoriaClinica.model.AnalysisModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnalysisRepository extends JpaRepository<AnalysisModel, Long> {
    void deleteByName(String name);
}
