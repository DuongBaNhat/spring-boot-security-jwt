package com.example.demo.service;

import com.example.demo.dao.UserDAO;
import com.example.demo.entities.User;
import com.sun.xml.internal.ws.handler.HandlerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserService implements InterfaceService<User> {
    @Autowired
    private UserDAO userDAO;

    @Override
    public List<User> getAll() throws HandlerException {
        return this.userDAO.getAll();
    }

    @Override
    public User getOne(String id) {
        return this.userDAO.getOne(id);
    }

    @Override
    public void delete(String id) {
        this.userDAO.delete(id);
    }

    @Override
    public void create(User o) {
        this.userDAO.create(o);
    }

    @Override
    public void update(String id, User o) {
        this.userDAO.update(id, o);
    }

    public boolean checkLogin(User user) {
        return this.userDAO.checkLogin(user);
    }

    public User getUserByName(String name) {
        return this.userDAO.getUserByName(name);
    }
  }
