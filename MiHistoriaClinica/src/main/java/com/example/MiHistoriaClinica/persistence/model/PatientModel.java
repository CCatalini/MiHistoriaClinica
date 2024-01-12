package com.example.MiHistoriaClinica.persistence.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "Patient")
public class PatientModel {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long patientId;

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

    @Column(name = "link_code")
    private String linkCode;




    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "patient_medic",
            joinColumns = @JoinColumn(name = "patientId"),
            inverseJoinColumns = @JoinColumn(name = "medicId")
    )
    @JsonManagedReference
    @JsonIgnoreProperties("patients")
    private List<MedicModel> medics = new ArrayList<>();


    @OneToOne(mappedBy = "patient", cascade = CascadeType.ALL)
    private MedicalHistoryModel medicalHistory;


    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "patient_medicine",
            joinColumns = @JoinColumn(name = "patientId"),
            inverseJoinColumns = @JoinColumn(name = "medicineId")
    )
    @JsonManagedReference
    private List<MedicineModel> medicines = new ArrayList<>();


    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "patient_analysis",
            joinColumns = @JoinColumn(name = "patientId"),
            inverseJoinColumns = @JoinColumn(name = "analysisId")
    )
    @JsonManagedReference
    private List<AnalysisModel> analysis = new ArrayList<>();


    @OneToMany(mappedBy = "patient", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonBackReference
    private List<MedicalAppointmentModel> medicalAppointments;

    @OneToMany(mappedBy = "patient", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Turnos> turnos;


    public List<MedicalAppointmentModel> getMedicalAppointments() {
        return medicalAppointments;
    }

    public void setMedicalAppointments(List<MedicalAppointmentModel> medicalAppointments) {
        this.medicalAppointments = medicalAppointments;
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

    public MedicalHistoryModel getMedicalHistory() {
        return medicalHistory;
    }

    public void setMedicalHistory(MedicalHistoryModel medicalHistory) {
        this.medicalHistory = medicalHistory;
    }

    public List<AnalysisModel> getAnalysis() {
        return analysis;
    }

    public void setAnalysis(List<AnalysisModel> analysis) {
        this.analysis = analysis;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patient_id) {
        this.patientId = patient_id;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PatientModel patient = (PatientModel) o;
        return Objects.equals(patientId, patient.patientId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(patientId);
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