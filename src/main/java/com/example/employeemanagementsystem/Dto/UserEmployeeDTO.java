package com.example.employeemanagementsystem.Dto;

import com.example.employeemanagementsystem.Entities.Department;
import com.example.employeemanagementsystem.Entities.Role;
import com.example.employeemanagementsystem.util.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserEmployeeDTO {
    private String email;
    private String password;
    private UserRole roleName;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private Department department;
}


