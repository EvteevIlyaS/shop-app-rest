package com.ilyaevteev.shopapp.repository.dao;

import com.ilyaevteev.shopapp.model.auth.Role;
import com.ilyaevteev.shopapp.repository.RoleRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
public class RoleRepositoryImpl implements RoleRepository {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Role findByName(String name) {
        Session session = sessionFactory.getCurrentSession();
        Query query= sessionFactory.getCurrentSession().
                createQuery("from Role where name = :name");
        query.setParameter("name", name);
        return (Role) query.uniqueResult();
    }
}
