package com.example.demo.service;

import com.sun.xml.internal.ws.handler.HandlerException;

import java.util.List;

public interface InterfaceService<T> {
    public List<T> getAll() throws HandlerException;
    public T getOne(String id);
    public void delete(String id);
    public void create(T o);
    public void update(String id, T o);
}
