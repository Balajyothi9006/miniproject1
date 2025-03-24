package com.example.EmployeeManagement;


import com.example.EmployeeManagement.Controller.EmployeeController;
import com.example.EmployeeManagement.Entity.Employee;
import com.example.EmployeeManagement.Service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class EmployeeControllerTest {

    private MockMvc mockMvc;

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeController employeeController;

    private Employee employee1;
    private Employee employee2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();

        employee1 = new Employee(1l, "John", "Doe", "eee","john.doe@example.com");
        employee2 = new Employee(2l, "Jane", "Smith","cse", "jane.smith@example.com");
    }

    @Test
    void testGetAllEmployees() throws Exception {
        List<Employee> employees = Arrays.asList(employee1, employee2);
        when(employeeService.getAllEmployees()).thenReturn(employees);

        mockMvc.perform(get("/api/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2));

        verify(employeeService, times(1)).getAllEmployees();
    }

    @Test
    void testAddEmployee() throws Exception {
        when(employeeService.saveEmployee(any(Employee.class))).thenReturn(employee1);

        mockMvc.perform(post("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"firstName\":\"John\",\"lastName\":\"Doe\",\"department\":\"HR\",\"email\":\"john.doe@example.com\"}"))
                .andExpect(status().isOk());

        verify(employeeService, times(1)).saveEmployee(any(Employee.class));
    }

    @Test
    void testDeleteEmployee() throws Exception {
        doNothing().when(employeeService).deleteEmployee(1L);

        mockMvc.perform(delete("/api/employees/1"))
                .andExpect(status().isOk());

        verify(employeeService, times(1)).deleteEmployee(1L);
    }
}
