package com.example.MiHistoriaClinica.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="laboratory")
public class LabModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long laboratorio_id;

    private String direction, city;

    @Column(name="healt_registration_number")
    private Long healthRegistrationNumber ;

    private String contactEmail;

    //"un laboratorio tiene muchas medicinas"
    @OneToMany(mappedBy = "lab", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MedicineModel> medicines = new ArrayList<>();
    /* orphanRemoval = true
    Cuando se establece en true, significa que si se elimina una "Medicina" de la lista de medicinas de un "Laboratorio"
    y esta "Medicina" no está siendo utilizada por otros objetos en la base de datos,
    entonces se eliminará automáticamente de la base de datos
     */

    public Long getLaboratorio_id() {
        return laboratorio_id;
    }

    public void setLaboratorio_id(Long laboratorio_id) {
        this.laboratorio_id = laboratorio_id;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Long getHealthRegistrationNumber() {
        return healthRegistrationNumber;
    }

    public void setHealthRegistrationNumber(Long healthRegistrationNumber) {
        this.healthRegistrationNumber = healthRegistrationNumber;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public List<MedicineModel> getMedicines() {
        return medicines;
    }

    public void setMedicines(List<MedicineModel> medicines) {
        this.medicines = medicines;
    }
}
