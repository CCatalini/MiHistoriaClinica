package com.example.MiHistoriaClinica.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "role")
public class RoleModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long role_id;

    @Column(name = "role_name")
    private String role;


    @ManyToMany(mappedBy = "userRoles")
    private List<UserModel> users;


    public void setRole_id(Long roleId) {
        this.role_id = roleId;
    }

    public Long getRole_id() {
        return role_id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
