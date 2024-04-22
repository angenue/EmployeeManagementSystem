package com.example.employeemanagementsystem.Service;

import com.example.employeemanagementsystem.Dto.UserEmployeeDTO;
import com.example.employeemanagementsystem.Entities.Department;
import com.example.employeemanagementsystem.Entities.Employee;
import com.example.employeemanagementsystem.Entities.User;

import java.util.List;

public interface DepartmentService {

    Department createDepartment(Department department);

    Department getDepartmentById(Long id);


    void deleteDepartmentById(Long id);

    // Methods for managing employees within departments

    List<Employee> getEmployeesInDepartment(Long departmentId);

    void removeEmployeeFromDepartment(Long departmentId, Long employeeId);

    // Methods for managing the department's manager
    Employee getDepartmentManager(Long departmentId);
}
