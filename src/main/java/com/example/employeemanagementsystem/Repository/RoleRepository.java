package com.example.employeemanagementsystem.Repository;

import com.example.employeemanagementsystem.Entities.Role;
import com.example.employeemanagementsystem.util.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(UserRole  roleName);
}
