package com.example.departmentservice.controller;

import com.example.departmentservice.client.EmployeeClient;
import com.example.departmentservice.model.Department;
import com.example.departmentservice.model.DepartmentEntity;
import com.example.departmentservice.repository.DepartmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/department")
public class DepartmentController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentController.class);

    @Autowired
    private DepartmentRepository repository;

    @Autowired
    private EmployeeClient employeeClient;

    @PostMapping
    public DepartmentEntity add(@RequestBody DepartmentEntity departmentEntity){
        LOGGER.info("Department add: {}", departmentEntity);
        return repository.save(departmentEntity);
    }

    @GetMapping
    public List<DepartmentEntity> findAll(){
        LOGGER.info("Department find");
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<DepartmentEntity> findById(@PathVariable Long id){
        LOGGER.info("Department find: id={}", id);
        return repository.findById(id);
    }

    @GetMapping("/with-employees")
    public List<Department> findAllWithEmployees() {
        LOGGER.info("Department find");
        return repository.findAll().stream()
                .map(departmentEntity -> {
                    Department department = new Department();
                    department.setId(departmentEntity.getId());
                    department.setName(departmentEntity.getName());
                    department.setEmployees(employeeClient.findByDepartment(department.getId()));
                    return department;
                })
                .collect(Collectors.toList());
    }

}
