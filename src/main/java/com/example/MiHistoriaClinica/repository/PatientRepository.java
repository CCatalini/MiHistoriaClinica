package com.example.MiHistoriaClinica.repository;


import com.example.MiHistoriaClinica.model.PatientModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<PatientModel, Long> {


}
