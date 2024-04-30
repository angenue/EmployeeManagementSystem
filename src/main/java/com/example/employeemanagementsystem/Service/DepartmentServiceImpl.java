package com.example.employeemanagementsystem.Service;

import com.example.employeemanagementsystem.Entities.Department;
import com.example.employeemanagementsystem.Entities.Employee;
import com.example.employeemanagementsystem.Repository.DepartmentRepository;
import com.example.employeemanagementsystem.util.UserRole;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartmentServiceImpl implements DepartmentService{

    private final DepartmentRepository departmentRepository;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    public Department createDepartment(String departmentName) {
        Department department = new Department();
        department.setName(departmentName); // Set the department name
        return departmentRepository.save(department);
    }

    @Override
    public Department getDepartmentById(Long id) {
        return departmentRepository.findById(id).orElse(null);
    }


    @Override
    public void deleteDepartmentById(Long id) {
        departmentRepository.deleteById(id);

    }

    @Override
    public List<Employee> getEmployeesInDepartment(Long departmentId) {
        Department department = getDepartmentById(departmentId);
        if (department != null) {
            // Filter out employees with a role other than "Employee"
            return department.getEmployees().stream()
                    .filter(employee -> employee.getUser().getRoleName() == UserRole.EMPLOYEE)
                    .collect(Collectors.toList());
        } else {
            return null;
        }
    }

    @Override
    public Employee getDepartmentManager(Long departmentId) {
        Department department = getDepartmentById(departmentId);
        return department != null ? department.getManager() : null;
    }

}
