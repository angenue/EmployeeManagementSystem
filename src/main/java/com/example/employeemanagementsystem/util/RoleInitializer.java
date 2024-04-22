package com.example.employeemanagementsystem.util;

import com.example.employeemanagementsystem.Entities.Role;
import com.example.employeemanagementsystem.Repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class RoleInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;

    public RoleInitializer(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) {
        // Check if roles are already populated
        if (roleRepository.count() == 0) {
            // Insert initial roles
            roleRepository.save(new Role(UserRole.EMPLOYEE));
            roleRepository.save(new Role(UserRole.MANAGER));
            roleRepository.save(new Role(UserRole.CEO));
        }
    }
}

