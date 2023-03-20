package com.ilyaevteev.shopapp.repository.dao;

import com.ilyaevteev.shopapp.model.Product;
import com.ilyaevteev.shopapp.repository.ProductRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductRepositoryImpl implements ProductRepository {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Product findByName(String name) {
        Session session = sessionFactory.getCurrentSession();
        Query query= session.createQuery("from Product where name = :name");
        query.setParameter("name", name);

        return (Product) query.uniqueResult();
    }

    @Override
    public void saveProduct(Product product) {
        Session session = sessionFactory.getCurrentSession();
        session.save(product);
    }

    @Override
    public void deleteProduct(String name) {
        Session session = sessionFactory.getCurrentSession();
        Query<Product> query = session.createQuery("delete from Product" +
                " where name = :name");
        query.setParameter("name", name);
        query.executeUpdate();
    }

    @Override
    public List<Product> getAll() {
        Session session = sessionFactory.getCurrentSession();
        Query<Product> query = session.createQuery("from Product");
        return query.getResultList();
    }

    @Override
    public Product findById(Long id) {
        Session session = sessionFactory.getCurrentSession();

        return session.get(Product.class, id);
    }
}
