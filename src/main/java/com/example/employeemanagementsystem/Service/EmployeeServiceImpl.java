package com.example.employeemanagementsystem.Service;

import com.example.employeemanagementsystem.Dto.UserEmployeeDTO;
import com.example.employeemanagementsystem.Entities.Department;
import com.example.employeemanagementsystem.Entities.Employee;
import com.example.employeemanagementsystem.Entities.Role;
import com.example.employeemanagementsystem.Entities.User;
import com.example.employeemanagementsystem.Repository.DepartmentRepository;
import com.example.employeemanagementsystem.Repository.RoleRepository;
import com.example.employeemanagementsystem.util.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    private final DepartmentRepository departmentRepository;
    private PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public EmployeeServiceImpl(DepartmentRepository departmentRepository, RoleRepository roleRepository) {
        this.departmentRepository = departmentRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public void addEmployee(UserEmployeeDTO userEmployeeDTO, Long departmentId) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new IllegalArgumentException("Department not found"));

        User user = createUserFromDTO(userEmployeeDTO);
        Role role = roleRepository.findByRoleName(UserRole.EMPLOYEE)
                .orElseThrow(() -> new IllegalArgumentException("Employee role not found"));
        user.setRole(role);

        Employee employee = createEmployeeFromDTO(userEmployeeDTO, department);
        employee.setUser(user);

        department.getEmployees().add(employee);
        departmentRepository.save(department);
    }

    @Override
    public void addManager(UserEmployeeDTO userEmployeeDTO, Long departmentId) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new IllegalArgumentException("Department not found"));

        User user = createUserFromDTO(userEmployeeDTO);
        Role role = roleRepository.findByRoleName(UserRole.MANAGER)
                .orElseThrow(() -> new IllegalArgumentException("Manager role not found"));
        user.setRole(role);

        Employee manager = createEmployeeFromDTO(userEmployeeDTO, department);
        manager.setUser(user);

        department.setManager(manager);
        departmentRepository.save(department);
    }

    @Override
    public void editEmployeeLogin(UserEmployeeDTO userEmployeeDTO, Long employeeId) {

    }

    @Override
    public void editEmployeeDetails(UserEmployeeDTO userEmployeeDTO, Long employeeId) {

    }

    // Helper method to create User entity from DTO
    private User createUserFromDTO(UserEmployeeDTO userEmployeeDTO) {
        User user = new User();
        user.setEmail(userEmployeeDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userEmployeeDTO.getPassword()));
        return user;
    }

    // Helper method to create Employee entity from DTO
    private Employee createEmployeeFromDTO(UserEmployeeDTO userEmployeeDTO, Department department) {
        Employee employee = new Employee();
        employee.setDepartment(department);
        employee.setFirstName(userEmployeeDTO.getFirstName());
        employee.setLastName(userEmployeeDTO.getLastName());
        employee.setPhoneNumber(userEmployeeDTO.getPhoneNumber());
        return employee;
    }
}
