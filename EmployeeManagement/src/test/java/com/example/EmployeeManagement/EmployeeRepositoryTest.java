package com.example.EmployeeManagement;




import com.example.EmployeeManagement.Entity.Employee;
import com.example.EmployeeManagement.Repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    private Employee employee;

    @BeforeEach
    void setUp() {
        employee = new Employee(1L, "John", "Doe","EEE", "john.doe@example.com");
        employeeRepository.save(employee);
    }

    @Test
    void testFindByEmail_Success() {
        Optional<Employee> foundEmployee = employeeRepository.findByEmail("john.doe@example.com");
        assertThat(foundEmployee).isPresent();
        assertThat(foundEmployee.get().getEmail()).isEqualTo(employee.getEmail());
    }

    @Test
    void testFindByEmail_NotFound() {
        Optional<Employee> foundEmployee = employeeRepository.findByEmail("nonexistent@example.com");
        assertThat(foundEmployee).isEmpty();
    }

    @Test
    void testDeleteEmployee() {
        employeeRepository.deleteById(employee.getId());
        Optional<Employee> deletedEmployee = employeeRepository.findById(employee.getId());
        assertThat(deletedEmployee).isEmpty();
    }

    @Test
    void testSaveEmployee() {
        Employee newEmployee = new Employee(null, "Jane", "Smith","EEE", "jane.smith@example.com");
        Employee savedEmployee = employeeRepository.save(newEmployee);
        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee.getId()).isNotNull();
    }
}

