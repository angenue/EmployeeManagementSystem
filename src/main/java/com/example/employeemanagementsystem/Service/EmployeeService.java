package com.example.employeemanagementsystem.Service;

import com.example.employeemanagementsystem.Dto.UserEmployeeDTO;

public interface EmployeeService {
    void addEmployee(UserEmployeeDTO userEmployeeDTO, Long departmentId);
    void addManager(UserEmployeeDTO userEmployeeDTO, Long departmentId);
    void editEmployeeLogin(UserEmployeeDTO userEmployeeDTO, Long employeeId);
    void editEmployeeDetails(UserEmployeeDTO userEmployeeDTO, Long employeeId);
}
