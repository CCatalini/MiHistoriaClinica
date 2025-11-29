package com.example.MiHistoriaClinica.persistence.model;

import com.example.MiHistoriaClinica.util.constant.MedicalSpecialtyE;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Entity
@Getter
@Setter
public class Medic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long medicId;
    private String name;
    private String lastname;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false, unique = true)
    private Long dni;
    @Column (nullable = false, unique= true)
    private Long matricula;
    private MedicalSpecialtyE specialty;
    private String password;

    @Column(name = "email_verified")
    private boolean emailVerified = false;

    @Column(name = "verification_token")
    private String verificationToken;

    @Column(name = "enabled")
    private boolean enabled = false;

    @ManyToMany(mappedBy = "medics", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Patient> patients = new ArrayList<>();


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Medic medic = (Medic) o;
        return Objects.equals(medicId, medic.medicId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(medicId);
    }
}
