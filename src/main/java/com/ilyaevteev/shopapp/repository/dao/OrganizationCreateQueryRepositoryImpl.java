package com.ilyaevteev.shopapp.repository.dao;

import com.ilyaevteev.shopapp.model.OrganizationCreateQuery;
import com.ilyaevteev.shopapp.repository.OrganizationCreateQueryRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class OrganizationCreateQueryRepositoryImpl implements OrganizationCreateQueryRepository {
    @Autowired
    SessionFactory sessionFactory;

    @Override
    public void saveOrganizationCreateQuery(OrganizationCreateQuery organizationCreateQuery) {
        Session session = sessionFactory.getCurrentSession();
        session.save(organizationCreateQuery);
    }

    @Override
    public OrganizationCreateQuery findById(Long id) {
        Session session = sessionFactory.getCurrentSession();

        return session.get(OrganizationCreateQuery.class, id);

    }

    @Override
    public void deleteById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Query<OrganizationCreateQuery> query = session.createQuery("delete from OrganizationCreateQuery" +
                " where id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }
}
