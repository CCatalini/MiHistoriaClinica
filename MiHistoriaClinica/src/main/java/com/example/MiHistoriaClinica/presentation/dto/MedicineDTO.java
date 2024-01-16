package com.example.MiHistoriaClinica.presentation.dto;

import com.example.MiHistoriaClinica.util.constant.MedicineNameAndDescription;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class MedicineDTO {

    private MedicineNameAndDescription medicineNameAndDescription;
    private String medicineDescription;
    private String comments;
    private String status;
    private LocalDate prescriptionDay;


    public MedicineDTO(MedicineNameAndDescription medicineNameAndDescription, String comments, String status, LocalDate prescriptionDay) {
        this.medicineNameAndDescription = medicineNameAndDescription;
        this.medicineDescription = medicineNameAndDescription.getDescription();
        this.comments = comments;
        this.status = status;
        this.prescriptionDay = prescriptionDay;
    }

}
