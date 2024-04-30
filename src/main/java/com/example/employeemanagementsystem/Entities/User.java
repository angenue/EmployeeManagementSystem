package com.example.employeemanagementsystem.Entities;

import com.example.employeemanagementsystem.util.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User { //storing the user credentials

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    @Email(message = "Please provide a valid email address")
    private String email;

    private String password;
    @Enumerated(EnumType.STRING)
    private UserRole roleName; // Store the role name directly

    @OneToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

}
