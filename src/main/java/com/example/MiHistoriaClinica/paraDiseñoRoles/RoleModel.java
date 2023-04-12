package com.example.MiHistoriaClinica.paraDiseñoRoles;

import com.example.MiHistoriaClinica.model.UserModel;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "role")
public class RoleModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long role_id;

    @Column(name = "role_name")
    private String role;


    // la relación inversa es también de muchos a muchos, y que la clave foránea en la tabla "user_role" se llama "role_id".
    @ManyToMany(mappedBy = "roles")
    private List<UserModel> users = new ArrayList<>();

    @OneToMany(mappedBy = "characteristic")
    private List<RoleCharacteristic> characteristics;


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

    public List<UserModel> getUsers() {
        return users;
    }

    public void setUsers(List<UserModel> users) {
        this.users = users;
    }

    public List<RoleCharacteristic> getCharacteristics() {
        return characteristics;
    }

    public void setCharacteristics(List<RoleCharacteristic> characteristics) {
        this.characteristics = characteristics;
    }
}
