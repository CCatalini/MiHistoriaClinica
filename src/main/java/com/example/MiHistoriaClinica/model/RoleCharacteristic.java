package com.example.MiHistoriaClinica.model;

import jakarta.persistence.*;

@Entity
@Table(name = "characteristics")
public class RoleCharacteristic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long characteristic_id;

    @Column(name="characteristic")
    private String characteristic;


    @ManyToOne
    @JoinColumn(name = "role_id")
    private RoleModel role;


    public Long getCharacteristic_id() {
        return characteristic_id;
    }

    public void setCharacteristic_id(Long characteristic_id) {
        this.characteristic_id = characteristic_id;
    }

    public String getCharacteristic() {
        return characteristic;
    }

    public void setCharacteristic(String characteristic) {
        this.characteristic = characteristic;
    }

    public RoleModel getRole() {
        return role;
    }

    public void setRole(RoleModel role) {
        this.role = role;
    }
}
