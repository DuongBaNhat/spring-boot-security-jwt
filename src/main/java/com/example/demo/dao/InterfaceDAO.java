package com.example.demo.dao;

import com.sun.xml.internal.ws.handler.HandlerException;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface InterfaceDAO<T> {
    public List<T> getAll() throws HandlerException;
    public T getOne(String id);
    public void delete(String id);
    public void create(T o);
    public void update(String id, T o);
}
