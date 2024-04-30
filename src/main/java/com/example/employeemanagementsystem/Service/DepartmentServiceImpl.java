package com.example.employeemanagementsystem.Service;

import com.example.employeemanagementsystem.Entities.Department;
import com.example.employeemanagementsystem.Entities.Employee;
import com.example.employeemanagementsystem.Repository.DepartmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return department != null ? department.getEmployees() : null; //return list of employees
    }


    //removes employee by id
   /* @Override
    public void removeEmployeeFromDepartment(Long departmentId, Long employeeId) {
        Department department = getDepartmentById(departmentId);
        if (department != null) {
            if (department.getManager() != null && department.getManager().getId().equals(employeeId)) {
                department.setManager(null); // Remove the manager
            }
            department.getEmployees().removeIf(employee -> employee.getId().equals(employeeId));
            departmentRepository.save(department);
        }
    }*/

    @Override
    public Employee getDepartmentManager(Long departmentId) {
        Department department = getDepartmentById(departmentId);
        return department != null ? department.getManager() : null;
    }

}
