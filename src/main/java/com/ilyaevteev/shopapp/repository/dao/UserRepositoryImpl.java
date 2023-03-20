package com.ilyaevteev.shopapp.repository.dao;

import com.ilyaevteev.shopapp.model.auth.User;
import com.ilyaevteev.shopapp.repository.UserRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public User findByUsername(String username) {
        Session session = sessionFactory.getCurrentSession();
        Query query= session.createQuery("from User where username = :username");
        query.setParameter("username", username);

        return (User) query.uniqueResult();
    }

    @Override
    public User save(User user) {
        Session session = sessionFactory.getCurrentSession();
        session.save(user);

        return user;
    }

    @Override
    public List<User> findAll() {
        Session session = sessionFactory.getCurrentSession();
        Query<User> query = session.createQuery("from User",
                User.class);

        return query.getResultList();
    }

    @Override
    public User findById(Long id) {
        Session session = sessionFactory.getCurrentSession();

        return session.get(User.class, id);
    }

    @Override
    public void deleteById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Query<User> query = session.createQuery("delete from User" +
                " where id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }
}
