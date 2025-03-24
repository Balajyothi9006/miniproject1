package com.example.EmployeeManagement.Repository;

import com.example.EmployeeManagement.Entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

// EmployeeRepository.java
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findByFirstNameContainingOrLastNameContaining(String firstName, String lastName);

    Optional<Employee> findByEmail(String email);
}
