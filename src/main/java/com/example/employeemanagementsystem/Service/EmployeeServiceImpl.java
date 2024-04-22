package com.example.employeemanagementsystem.Service;

import com.example.employeemanagementsystem.Dto.UserEmployeeDTO;
import com.example.employeemanagementsystem.Entities.Department;
import com.example.employeemanagementsystem.Entities.Employee;
import com.example.employeemanagementsystem.Entities.Role;
import com.example.employeemanagementsystem.Entities.User;
import com.example.employeemanagementsystem.Repository.DepartmentRepository;
import com.example.employeemanagementsystem.Repository.EmployeeRepository;
import com.example.employeemanagementsystem.Repository.RoleRepository;
import com.example.employeemanagementsystem.Repository.UserRepository;
import com.example.employeemanagementsystem.util.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;
    private final UserRepository userRepository;
    //private PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public EmployeeServiceImpl(DepartmentRepository departmentRepository, RoleRepository roleRepository, EmployeeRepository employeeRepository, UserRepository userRepository) {
        this.departmentRepository = departmentRepository;
        this.roleRepository = roleRepository;
        this.employeeRepository = employeeRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void addEmployee(UserEmployeeDTO userEmployeeDTO, Long departmentId) {
        //need to add employee to department
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new IllegalArgumentException("Department not found"));

        //creating login info
        User user = createUserFromDTO(userEmployeeDTO);
        Role role = roleRepository.findByRoleName(UserRole.EMPLOYEE)
                .orElseThrow(() -> new IllegalArgumentException("Employee role not found"));
        user.setRole(role);

        //employee info
        Employee employee = createEmployeeFromDTO(userEmployeeDTO, department);
        employee.setUser(user);

        department.getEmployees().add(employee);
        departmentRepository.save(department);
    }

    @Override
    public void addManager(UserEmployeeDTO userEmployeeDTO, Long departmentId) {
        //need to add employee to department
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new IllegalArgumentException("Department not found"));

        //creating login info
        User user = createUserFromDTO(userEmployeeDTO);
        Role role = roleRepository.findByRoleName(UserRole.MANAGER)
                .orElseThrow(() -> new IllegalArgumentException("Manager role not found"));
        user.setRole(role);

        //employee info
        Employee manager = createEmployeeFromDTO(userEmployeeDTO, department);
        manager.setUser(user);

        department.setManager(manager);
        departmentRepository.save(department);
    }

    @Override
    public void editEmployeeField(Long employeeId, String fieldName, Object newValue) {
        // Retrieve the Employee entity
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new IllegalArgumentException("Employee not found"));

        // Update the specified field based on the fieldName
        switch (fieldName) {
            case "email" -> {
                User user = employee.getUser();
                user.setEmail((String) newValue);
                userRepository.save(user);
            }
            case "password" -> {
                User user = employee.getUser();
                //user.setPassword(passwordEncoder.encode((String) newValue));
                user.setPassword((String) newValue);
                userRepository.save(user);
            }
            case "firstName" -> {
                employee.setFirstName((String) newValue);
                employeeRepository.save(employee);
            }
            case "lastName" -> {
                employee.setLastName((String) newValue);
                employeeRepository.save(employee);
            }
            case "phoneNumber" -> {
                employee.setPhoneNumber((String) newValue);
                employeeRepository.save(employee);
            }
            case "role" -> {
                Role role = roleRepository.findById((Long) newValue)
                        .orElseThrow(() -> new IllegalArgumentException("Role not found"));
                employee.getUser().setRole(role);
                userRepository.save(employee.getUser());
            }
            case "department" -> {
                Department department = departmentRepository.findById((Long) newValue)
                        .orElseThrow(() -> new IllegalArgumentException("Department not found"));
                employee.setDepartment(department);
                employeeRepository.save(employee);
            }
            default -> throw new IllegalArgumentException("Invalid field name");
        }
    }

    // Helper method to create User entity from DTO
    private User createUserFromDTO(UserEmployeeDTO userEmployeeDTO) {
        User user = new User();
        user.setEmail(userEmployeeDTO.getEmail());
        //user.setPassword(passwordEncoder.encode(userEmployeeDTO.getPassword()));
        user.setPassword(userEmployeeDTO.getPassword());
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
