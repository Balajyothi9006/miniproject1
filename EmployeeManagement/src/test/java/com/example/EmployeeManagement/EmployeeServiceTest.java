package com.example.EmployeeManagement;
import com.example.EmployeeManagement.Entity.Employee;
import com.example.EmployeeManagement.Exception.EmailAlreadyExistsException;
import com.example.EmployeeManagement.Repository.EmployeeRepository;
import com.example.EmployeeManagement.Service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    private Employee employee;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        employee = new Employee(1L, "John", "Doe", "HR", "john.doe@example.com");
    }

    @Test
    void testSaveEmployee_Success() {
        when(employeeRepository.findByEmail(employee.getEmail())).thenReturn(Optional.empty());
        when(employeeRepository.save(employee)).thenReturn(employee);

        Employee savedEmployee = employeeService.saveEmployee(employee);

        assertNotNull(savedEmployee);
        assertEquals("John", savedEmployee.getFirstName());
        assertEquals("HR", savedEmployee.getDepartment());
    }

    @Test
    void testSaveEmployee_EmailAlreadyExists() {
        when(employeeRepository.findByEmail(employee.getEmail())).thenReturn(Optional.of(employee));

        assertThrows(EmailAlreadyExistsException.class, () -> {
            employeeService.saveEmployee(employee);
        });

        verify(employeeRepository, never()).save(any(Employee.class));
    }

    @Test
    void testGetEmployeeById_Success() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        Employee fetchedEmployee = employeeService.getEmployeeById(1L);
        assertNotNull(fetchedEmployee);
        assertEquals(1L, fetchedEmployee.getId());
        assertEquals("HR", fetchedEmployee.getDepartment());
    }

    @Test
    void testDeleteEmployee() {
        doNothing().when(employeeRepository).deleteById(1L);

        employeeService.deleteEmployee(1L);
        verify(employeeRepository, times(1)).deleteById(1L);
    }
}
