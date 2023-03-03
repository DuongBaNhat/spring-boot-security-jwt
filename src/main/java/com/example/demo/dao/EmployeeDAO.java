package com.example.demo.dao;

import com.example.demo.dto.EmployeeDto;
import com.example.demo.entities.Employee;
import com.sun.xml.internal.ws.handler.HandlerException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Repository
public class EmployeeDAO {
    @Autowired
    private SessionFactory sessionFactory;

    public List<Employee> getAll() throws HandlerException {
       Session session = this.sessionFactory.getCurrentSession();
       return  session.createQuery("FROM " + Employee.class.getName()).list();
    }

    public Employee getOne(String id) {
        Session session = this.sessionFactory.getCurrentSession();
        return session.get( Employee.class, id);
    }

    public void delete(String id) {
       Session session = this.sessionFactory.getCurrentSession();
       Employee employee = session.get(Employee.class, id);
       session.delete(employee);
    }
    public void create(EmployeeDto e) {
        Employee employee = new Employee(e.getName());
        Session session = this.sessionFactory.getCurrentSession();
        session.save(employee);
    }

    public void update(String id, EmployeeDto e) {
        Session session = this.sessionFactory.getCurrentSession();
        Employee employee = new Employee(id, e.getName());
        session.update(employee);
    }
}
