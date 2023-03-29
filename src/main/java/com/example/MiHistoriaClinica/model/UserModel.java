package com.example.MiHistoriaClinica.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "user")
public class UserModel {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long user_id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String lastname;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private Date birthdate;

    @Column(unique = true, nullable = false)
    private Long dni;



    public void setId(Long id) {
        this.user_id = id;
    }

    public Long getId() {
        return user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String username) {
        this.name = username;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String password) {
        this.lastname = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public Long getDni() {
        return dni;
    }

    public void setDni(Long dni) {
        this.dni = dni;
    }
}