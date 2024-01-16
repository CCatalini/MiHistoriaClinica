package com.example.MiHistoriaClinica.persistence.model;

import com.example.MiHistoriaClinica.util.constant.MedicineName;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Medicine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long medicineId;
    @Enumerated(EnumType.STRING) private MedicineName medicineName;
    private String medicineDescription;
    private String comments;
    private String status;
    @DateTimeFormat(pattern = "yyyy-MM-dd") private LocalDate prescriptionDay;


    @ManyToMany(mappedBy = "medicines")
    @JsonBackReference
    private List<Patient> patients = new ArrayList<>();

    public void addPatient(Patient patient){
        patients.add(patient);
    }

}
