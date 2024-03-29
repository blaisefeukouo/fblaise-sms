package com.fblaise.sms.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.fblaise.sms.model.Employee;

@Repository("employeeRepository")
@CrossOrigin(origins = "http://localhost:4200")
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

	Employee findByUserName(String userName);

	Optional<Employee> findById(Long id);
}