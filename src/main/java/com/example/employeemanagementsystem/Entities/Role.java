package com.example.employeemanagementsystem.Entities;

import com.example.employeemanagementsystem.util.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING) //the only roles are manager and employee
    private UserRole roleName;

    public Role(UserRole roleName) {
        this.roleName = roleName;
    }
}
