package com.example.MiHistoriaClinica.persistence.repository;

import com.example.MiHistoriaClinica.persistence.model.MedicineModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicineRepository extends JpaRepository<MedicineModel, Long> {
    MedicineModel findByMedicineNameAndLab(String medicineName, String lab);
}
