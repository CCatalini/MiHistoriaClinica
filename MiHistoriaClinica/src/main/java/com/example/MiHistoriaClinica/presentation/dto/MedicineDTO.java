package com.example.MiHistoriaClinica.presentation.dto;

import com.example.MiHistoriaClinica.util.constant.MedicineName;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class MedicineDTO {

    private MedicineName medicineName;
    private String medicineDescription;
    private String comments;
    private String status;
    private LocalDate prescriptionDay;


    public MedicineDTO(MedicineName medicineName, String comments, String status, LocalDate prescriptionDay) {
        this.medicineName = medicineName;
        this.medicineDescription = medicineName.getDescription();
        this.comments = comments;
        this.status = status;
        this.prescriptionDay = prescriptionDay;
    }

}
