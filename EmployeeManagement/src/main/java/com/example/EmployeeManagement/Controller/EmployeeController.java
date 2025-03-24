package com.example.EmployeeManagement.Controller;

import com.example.EmployeeManagement.Entity.Employee;
import com.example.EmployeeManagement.Service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/")
    public String showEmployeeList(Model model) {
        List<Employee> employees = employeeService.getAllEmployees();
        model.addAttribute("employees", employees);
        return "index"; // Loads index.html
    }

    @GetMapping("/add-employee")
    public String showAddEmployeeForm(Model model) {
        model.addAttribute("employee", new Employee());
        return "add-employee"; // Loads add-employee.html
    }

    @PostMapping("/add-employee")
    public String saveEmployee(@ModelAttribute("employee") Employee employee) {
        employeeService.saveEmployee(employee);
        return "redirect:/";
    }

    @GetMapping("/edit-employee/{id}")
    public String showEditEmployeeForm(@PathVariable Long id, Model model) {
        Employee employee = employeeService.getEmployeeById(id);
        model.addAttribute("employee", employee);
        return "edit-employee"; // Loads edit-employee.html
    }
    @PostMapping("/edit-employee/{id}")
    public String updateEmployee(@PathVariable Long id,@ModelAttribute("employee") Employee employee) {

        employee.setId(id);
        employeeService.saveEmployee(employee);
        return "redirect:/";
    }

    @GetMapping("/view-employee/{id}")
    public String showEmployeeProfile(@PathVariable Long id, Model model) {
        Employee employee = employeeService.getEmployeeById(id);
        model.addAttribute("employee", employee);
        return "view-employee"; // Loads view-employees.html
    }

    @GetMapping("/delete-employee/{id}")
    public String deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return "redirect:/";
    }
}