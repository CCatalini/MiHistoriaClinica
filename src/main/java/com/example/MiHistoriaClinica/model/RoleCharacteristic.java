package com.example.MiHistoriaClinica.model;

import jakarta.persistence.*;

@Entity
@Table(name = "characteristics")
public class RoleCharacteristic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long characteristic_id;

    @Column(name="characteristic_name")
    private String characteristic;


    @ManyToOne
    @JoinColumn(name = "role_id")
    private RoleModel role;
}
