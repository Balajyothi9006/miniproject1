package com.example.EmployeeManagement.Service;

import com.example.EmployeeManagement.Entity.Employee;
import com.example.EmployeeManagement.Exception.EmailAlreadyExistsException;
import com.example.EmployeeManagement.Exception.ResourceNotFoundException;
import com.example.EmployeeManagement.Repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;


    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }


    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
    }

    public Employee saveEmployee(Employee employee) {
        Optional<Employee> existingEmployee = employeeRepository.findByEmail(employee.getEmail());
        if (existingEmployee.isPresent() && !existingEmployee.get().getId().equals(employee.getId())) {
            throw new EmailAlreadyExistsException("Email already exists: " + employee.getEmail());
        }
        return employeeRepository.save(employee);
    }


    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }

    public List<Employee> searchEmployees(String keyword) {
        return employeeRepository.findByFirstNameContainingOrLastNameContaining(keyword, keyword);
    }
}
