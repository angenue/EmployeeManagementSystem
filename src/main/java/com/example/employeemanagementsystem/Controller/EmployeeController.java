package com.example.employeemanagementsystem.Controller;

import com.example.employeemanagementsystem.Dto.UserEmployeeDTO;
import com.example.employeemanagementsystem.Service.DepartmentService;
import com.example.employeemanagementsystem.Service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    private final EmployeeService employeeService;
    private final DepartmentService departmentService;

    public EmployeeController(EmployeeService employeeService, DepartmentService departmentService) {
        this.employeeService = employeeService;
        this.departmentService = departmentService;
    }

    // Update employee
    @PatchMapping("/update/{employeeId}")
    public ResponseEntity<Void> updateEmployeeField(@RequestBody Map<String, Object> fieldsToUpdate, @PathVariable Long employeeId) {
        for (Map.Entry<String, Object> entry : fieldsToUpdate.entrySet()) {
            employeeService.editEmployeeField(employeeId, entry.getKey(), entry.getValue());
        }
        return ResponseEntity.noContent().build();
    }

    //remove employee
    @DeleteMapping("/delete/{employeeId}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long employeeId) {
        employeeService.removeEmployee(employeeId);
        return ResponseEntity.noContent().build();
    }
}
