package com.example.MiHistoriaClinica.persistence.repository;

import com.example.MiHistoriaClinica.persistence.model.MedicalAppointmentModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicalAppointmentRepository extends JpaRepository<MedicalAppointmentModel, Long> {
}
