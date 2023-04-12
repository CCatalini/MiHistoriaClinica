package com.example.MiHistoriaClinica.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Patient")
public class PatientModel {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long patient_id;

    @Column()
    private String name;

    @Column()
    private String lastname;

    @Column(unique = true)
    private Long dni;

    @Column()
    private String password;

    @Column()
    private String email;

    @Column()
    private Date birthdate;


    public Long getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(Long patient_id) {
        this.patient_id = patient_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Long getDni() {
        return dni;
    }

    public void setDni(Long dni) {
        this.dni = dni;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
}

/*
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_role",
            // joinColumns e inverseJoinColumns definen las claves foráneas de las tablas "user" y "role" en la tabla intermedia
            joinColumns = @JoinColumn(name = "patient_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<RoleModel> roles = new ArrayList<>();
*/


/*
    public List<RoleModel> getUserRoles() {
        return roles;
    }

    public void setUserRoles(List<RoleModel> userRoles) {
        this.roles = userRoles;
    }

 */