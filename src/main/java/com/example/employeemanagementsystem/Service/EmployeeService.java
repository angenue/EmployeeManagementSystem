package com.example.employeemanagementsystem.Service;

import com.example.employeemanagementsystem.Dto.UserEmployeeDTO;

public interface EmployeeService {
    void addEmployee(UserEmployeeDTO userEmployeeDTO, Long departmentId);
    void addManager(UserEmployeeDTO userEmployeeDTO, Long departmentId);
    void editEmployeeField(Long employeeId, String fieldName, Object newValue);
}
