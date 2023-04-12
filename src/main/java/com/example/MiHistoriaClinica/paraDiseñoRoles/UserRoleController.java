package com.example.MiHistoriaClinica.paraDiseñoRoles;

import com.example.MiHistoriaClinica.model.UserModel;
import com.example.MiHistoriaClinica.repository.UserRepository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/userRole")
public class UserRoleController {

    UserRepository userRepository;
    RoleRepository roleRepository;

    @PostMapping("/{usuarioId}/roles/{rolId}")
    public UserModel asignarRol(@PathVariable Long usuarioId, @PathVariable Long rolId) {
        UserModel usuario = userRepository.findById(usuarioId).orElse(null);
        RoleModel rol = roleRepository.findById(rolId).orElse(null);
        if (usuario != null && rol != null) {
            usuario.getUserRoles().add(rol);
            return userRepository.save(usuario);
        } else {
            return null;
        }
    }


}
