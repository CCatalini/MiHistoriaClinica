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

    @Column(nullable = false, unique = true)
    private Long dni;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @ManyToOne
    @JoinColumn(name="role_id")
    private Role role;


    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

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