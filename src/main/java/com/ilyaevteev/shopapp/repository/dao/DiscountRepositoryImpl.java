package com.ilyaevteev.shopapp.repository.dao;

import com.ilyaevteev.shopapp.model.Discount;
import com.ilyaevteev.shopapp.repository.DiscountRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
public class DiscountRepositoryImpl implements DiscountRepository {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Discount findById(Long id) {
        Session session = sessionFactory.getCurrentSession();

        return session.get(Discount.class, id);
    }

    @Override
    public void saveDiscount(Discount discount) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(discount);
    }

    @Override
    public void deleteDiscount(Long id) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(session.get(Discount.class, id));
    }
}
