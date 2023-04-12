package com.example.MiHistoriaClinica.paraDiseñoRoles;

import com.example.MiHistoriaClinica.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/roles")
public class RoleController {

    @Autowired
    private RoleRepository roleRepository;

    @PostMapping
    public RoleModel createRole(@RequestBody RoleModel rol) {
        return roleRepository.save(rol);
    }

    @GetMapping("/{id}")
    public RoleModel getRoleById(@PathVariable Long id) {
        return roleRepository.findById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public RoleModel updateRole(@PathVariable Long id, @RequestBody RoleModel newRole) {
        RoleModel role = roleRepository.findById(id).orElse(null);
        if (role != null) {
            role.setRole(newRole.getRole());
            role.setUsers(newRole.getUsers());
            return roleRepository.save(role);
        } else {
            return null;
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
        RoleModel existingRole = roleRepository.findById(id).orElseThrow(()
                              -> new ResourceNotFoundException("User not found"));
        roleRepository.delete(existingRole);
        return ResponseEntity.noContent().build();
    }
}