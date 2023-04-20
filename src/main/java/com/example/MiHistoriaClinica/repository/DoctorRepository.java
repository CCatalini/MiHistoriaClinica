package com.example.MiHistoriaClinica.repository;

import com.example.MiHistoriaClinica.model.DoctorModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<DoctorModel, Long> {

    DoctorModel findByDniAndPassword(Long dni, String password);
}
