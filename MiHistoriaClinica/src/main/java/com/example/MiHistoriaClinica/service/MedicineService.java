package com.example.MiHistoriaClinica.service;

import java.util.List;

public interface MedicineService {
    List<String> getAllMedicinesNames();

    String getMedicineDescription(String medicineName);
}
