package com.example.MiHistoriaClinica.repository;

import com.example.MiHistoriaClinica.model.MedicineModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicineRepository extends JpaRepository<MedicineModel, Long> {
    MedicineModel findByMedicineNameAndLab(String medicineName, String lab);
}
