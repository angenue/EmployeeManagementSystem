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
        user.setRoleName(UserRole.EMPLOYEE);


        //employee info
        Employee employee = createEmployeeFromDTO(userEmployeeDTO, department);

        user.setEmployee(employee);

        employeeRepository.save(employee);
        userRepository.save(user);
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
        user.setRoleName(UserRole.MANAGER);


        //employee info
        Employee manager = createEmployeeFromDTO(userEmployeeDTO, department);
        //manager.setUser(user);

        user.setEmployee(manager);

        employeeRepository.save(manager);
        userRepository.save(user);

        department.setManager(manager);
        departmentRepository.save(department);
    }

    @Override
    public void editEmployeeField(Long employeeId, String fieldName, Object newValue) {
        // Retrieve the Employee entity
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new IllegalArgumentException("Employee not found"));

        // Retrieve the User entity
        User user = userRepository.findByEmployeeId(employeeId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));


        // Update the specified field based on the fieldName
        switch (fieldName) {
            case "email" -> {
                user.setEmail((String) newValue);
                userRepository.save(user);
            }
            case "password" -> {
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
                UserRole newRole = (UserRole) newValue;
                user.setRoleName(newRole); // Update the role name directly
                userRepository.save(user);
            }// Save the updated user
            default -> throw new IllegalArgumentException("Invalid field name");
        }
    }

    @Override
    public void removeEmployee(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new IllegalArgumentException("Employee not found"));

        // Remove the user associated with the employee
        User user = employee.getUser();
        if (user != null) {
            userRepository.delete(user);
        }

        // Remove the employee from their department
        if (employee.getDepartment() != null) {
            Department department = employee.getDepartment();
            department.getEmployees().remove(employee);
            departmentRepository.save(department);
        }

        // Finally, delete the employee
        employeeRepository.delete(employee);
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
