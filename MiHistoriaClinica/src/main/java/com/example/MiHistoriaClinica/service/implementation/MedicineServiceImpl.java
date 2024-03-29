package com.example.MiHistoriaClinica.service.implementation;

import com.example.MiHistoriaClinica.persistence.repository.MedicineRepository;
import com.example.MiHistoriaClinica.service.MedicineService;
import com.example.MiHistoriaClinica.util.constant.MedicineE;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicineServiceImpl implements MedicineService {

    private final MedicineRepository medicineRepository;

    public MedicineServiceImpl(MedicineRepository medicineRepository) {
        this.medicineRepository = medicineRepository;
    }


    @Override
    public List<String> getAllMedicinesNames() {
        return MedicineE.getAllMedicinesNames();
    }

    @Override
    public String getMedicineDescription(String medicineName) {
        return MedicineE.getMedicineDescription(medicineName);
    }
}
