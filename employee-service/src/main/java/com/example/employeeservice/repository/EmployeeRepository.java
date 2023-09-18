package com.example.employeeservice.repository;

import com.example.employeeservice.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;


public interface EmployeeRepository extends JpaRepository<Employee,Long> {

    public List<Employee> findByDepartmentId(Long departmentId);

}
