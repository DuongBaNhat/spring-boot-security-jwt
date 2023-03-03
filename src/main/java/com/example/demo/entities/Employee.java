package com.example.demo.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Entity()
@Table(name = "employee")
public class Employee implements Serializable {
    private static final long serialVersionUID = 1L;
    private  String id;
    private String name;

    public Employee() {}
    public Employee( String name) {
        this.name = name;
    }
    public Employee(String id, String name) {
        this.name = name;
        this.id = id;
    }
    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
