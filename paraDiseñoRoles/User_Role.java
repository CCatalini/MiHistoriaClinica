package com.example.MiHistoriaClinica.paraDiseñoRoles;

import com.example.MiHistoriaClinica.model.UserModel;
import jakarta.persistence.*;


@Entity
@Table(name = "user_role")
public class User_Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserModel user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private UserModel role;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public UserModel getRole() {
        return role;
    }

    public void setRole(UserModel role) {
        this.role = role;
    }
}
