package com.example.MiHistoriaClinica.repository;

import com.example.MiHistoriaClinica.model.MedicalAppointmentModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicalAppointmentRepository extends JpaRepository<MedicalAppointmentModel, Long> {
}
