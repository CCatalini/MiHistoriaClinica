package com.example.MiHistoriaClinica.presentation.dto;

import com.example.MiHistoriaClinica.util.constant.MedicineName;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class MedicineDTO {

    private MedicineName name;
    private String description;
    private String comments;
    private String status;
    private LocalDate prescriptionDay;


    public MedicineDTO(MedicineName name, String comments, String status, LocalDate prescriptionDay) {
        this.name = MedicineName.valueOf(name.name());
        this.description = name.getDescription();
        this.comments = comments;
        this.status = status;
        this.prescriptionDay = prescriptionDay;
    }

}
