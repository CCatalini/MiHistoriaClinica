package com.example.MiHistoriaClinica.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @Column()
    private Date birthdate;

//todo revisar para borrar wsta clase
    @ManyToOne
    @JoinColumn(name="role_id")
    private Role role;


    @Column(name = "link_code", nullable = false, unique = true)
    private String linkCode;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "patient_medic",
            joinColumns = @JoinColumn(name = "patient_id"),
            inverseJoinColumns = @JoinColumn(name = "medic_id")
    )
    private List<MedicModel> medics = new ArrayList<>();

    @OneToMany(mappedBy = "patient", fetch = FetchType.EAGER)
    private List<MedicineModel> medicines;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "patient_analysis",
            joinColumns = @JoinColumn(name = "patient_id"),
            inverseJoinColumns = @JoinColumn(name = "analysis_id")
    )
    private List<AnalysisModel> analysis = new ArrayList<>();





    public List<AnalysisModel> getAnalysis() {
        return analysis;
    }

    public void setAnalysis(List<AnalysisModel> analysis) {
        this.analysis = analysis;
    }

    public List<MedicineModel> getMedicines() {
        return medicines;
    }

    public void setMedicines(List<MedicineModel> medicines) {
        this.medicines = medicines;
    }

    public List<MedicModel> getMedics() {
        return medics;
    }

    public void setMedics(List<MedicModel> medics) {
        this.medics = medics;
    }

    public String getLinkCode() {
        return linkCode;
    }

    public void setLinkCode(String linkCode) {
        this.linkCode = linkCode;
    }

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