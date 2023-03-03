package com.example.demo.service;

import com.example.demo.dao.EmployeeDAO;
import com.example.demo.dto.EmployeeDto;
import com.example.demo.entities.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class EmployeeService {
    @Autowired
    private EmployeeDAO employeeDAO;
    public List<Employee> getAll() {
        return this.employeeDAO.getAll();
    }

    public Employee getOne(String id) {
        return this.employeeDAO.getOne(id);
    }

    public void delete(String id) {
        this.employeeDAO.delete(id);
    }

    public void create(EmployeeDto e) {
        this.employeeDAO.create(e);
    }

    public void update(String id, EmployeeDto e) {
        this.employeeDAO.update(id, e);
    }
}
