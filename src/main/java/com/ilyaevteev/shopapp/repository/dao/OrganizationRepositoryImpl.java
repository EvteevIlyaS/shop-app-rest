package com.ilyaevteev.shopapp.repository.dao;

import com.ilyaevteev.shopapp.model.Organization;
import com.ilyaevteev.shopapp.repository.OrganizationRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class OrganizationRepositoryImpl implements OrganizationRepository {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Organization findByName(String name) {
        Session session = sessionFactory.getCurrentSession();
        Query query= session.createQuery("from Organization where name = :name");
        query.setParameter("name", name);

        return (Organization) query.uniqueResult();
    }

    @Override
    public Organization findById(Long id) {
        Session session = sessionFactory.getCurrentSession();

        return session.get(Organization.class, id);
    }

    @Override
    public void saveOrganization(Organization organization) {
        Session session = sessionFactory.getCurrentSession();
        session.save(organization);
    }
}
