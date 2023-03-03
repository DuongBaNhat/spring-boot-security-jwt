package com.example.demo.controller;

import com.example.demo.dto.EmployeeDto;
import com.example.demo.entities.Employee;
import com.example.demo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;
    //create
    @RequestMapping(
            value = "employees",
            method = RequestMethod.POST
    )
    public void create(@RequestBody() EmployeeDto e) {
        this.employeeService.create(e);
    }

    /**
     * getAll
     * @return
     */
    @RequestMapping(
            value = "/employees",
            method = RequestMethod.GET,
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE,
            }
    )
    @ResponseBody
    public List<Employee> getAll() {
        return this.employeeService.getAll();
    }

    //getOne
    @RequestMapping(value = "employees/{id}",
            method = RequestMethod.GET,
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE,
            }
    )
    @ResponseBody
    public Employee getOne(@PathVariable("id") String id) {
        return this.employeeService.getOne(id);
    }

    /**
     * Delete
     * @param id
     * @return
     */
    @RequestMapping(
            value = "employees/{id}",
            method = RequestMethod.DELETE
    )
    public void delete(@PathVariable("id") String id) {
        this.employeeService.delete(id);
    }

    /**
     * Update
     * @param id
     * @param e
     * @return
     */
    @RequestMapping(
            value = "employees/{id}",
            method = RequestMethod.PATCH
    )
    public void update(@PathVariable("id") String id,  @RequestBody() EmployeeDto e) {
        this.employeeService.update(id, e);
    }
}