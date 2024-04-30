package com.example.employeemanagementsystem.Controller;

import com.example.employeemanagementsystem.Dto.DepartmentDTO;
import com.example.employeemanagementsystem.Dto.UserEmployeeDTO;
import com.example.employeemanagementsystem.Entities.Department;
import com.example.employeemanagementsystem.Entities.Employee;
import com.example.employeemanagementsystem.Service.DepartmentService;
import com.example.employeemanagementsystem.Service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    private final EmployeeService employeeService;

    public DepartmentController(EmployeeService employeeService, DepartmentService departmentService) {
        this.employeeService = employeeService;
        this.departmentService = departmentService;
    }

    //create department
    @PostMapping
    public ResponseEntity<Department> createDepartment(@RequestBody DepartmentDTO departmentDTO) {
        Department createdDepartment = departmentService.createDepartment(departmentDTO.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDepartment);
    }

    //create employees
    //create an employee and manager
    @PostMapping("/{departmentId}/createManager")
    public ResponseEntity<Void> createManager(@RequestBody UserEmployeeDTO userEmployeeDTO, @PathVariable Long departmentId) {
        employeeService.addManager(userEmployeeDTO, departmentId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/{departmentId}/createEmployee")
    public ResponseEntity<Void> createEmployee(@RequestBody UserEmployeeDTO userEmployeeDTO, @PathVariable Long departmentId) {
        employeeService.addEmployee(userEmployeeDTO, departmentId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    //delete department


    // Retrieve all employees
    @GetMapping("/{departmentId}/employees")
    public ResponseEntity<List<Employee>> getEmployeesInDepartment(@PathVariable Long departmentId) {
        List<Employee> employees = departmentService.getEmployeesInDepartment(departmentId);
        return ResponseEntity.ok().body(employees);
    }

    // Retrieve the manager of a department
    @GetMapping("/{departmentId}/manager")
    public ResponseEntity<Employee> getDepartmentManager(@PathVariable Long departmentId) {
        Employee manager = departmentService.getDepartmentManager(departmentId);
        return ResponseEntity.ok().body(manager);
    }

}
