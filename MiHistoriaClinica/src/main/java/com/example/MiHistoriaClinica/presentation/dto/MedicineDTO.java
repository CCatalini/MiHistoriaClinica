package com.example.MiHistoriaClinica.presentation.dto;

import com.example.MiHistoriaClinica.util.constant.MedicineE;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class MedicineDTO {

    private MedicineE name;
    private String description;
    private String comments;
    private String status;
    private LocalDate prescriptionDay;


    public MedicineDTO(MedicineE name, String comments, String status, LocalDate prescriptionDay) {
        this.name = MedicineE.valueOf(name.name());
        this.description = name.getDescription();
        this.comments = comments;
        this.status = status;
        this.prescriptionDay = prescriptionDay;
    }

}
