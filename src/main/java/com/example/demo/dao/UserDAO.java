package com.example.demo.dao;

import com.example.demo.entities.User;
import com.sun.xml.internal.ws.handler.HandlerException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDAO implements InterfaceDAO<User> {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<User> getAll() throws HandlerException {
        Session session = this.sessionFactory.getCurrentSession();
        return session.createQuery("PROM", User.class).list();
    }

    @Override
    public User getOne(String id) {
        Session session = this.sessionFactory.getCurrentSession();
        return session.get(User.class, id);
    }

    @Override
    public void delete(String id) {
        Session session = this.sessionFactory.getCurrentSession();
        session.delete(id);
    }

    @Override
    public void create(User o) {
        Session session = this.sessionFactory.getCurrentSession();
        session.save(o);
    }

    @Override
    public void update(String id, User o) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(id, o);
    }

    public User getUserByName(String name) {Session session = this.sessionFactory.getCurrentSession();
        String hql = "FROM " + User.class.getName() + " where username = :username";
       Query<User> query =  session.createQuery(hql);
       query.setParameter("username", name);
       return query.uniqueResult();
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public boolean checkLogin(User user) {
        Session session = this.sessionFactory.getCurrentSession();
        String hql = "FROM " + User.class.getName() + " where username = :username and password = :password";
        Query<User> query = session.createQuery(hql);

        query.setParameter("username", user.getUsername());
        query.setParameter("password", user.getPassword());
        List<User> users = query.list();

        if(users != null && users.size() > 0) {
            return true;
        } else {
            return false;
        }
    }
}
