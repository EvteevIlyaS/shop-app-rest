package com.ilyaevteev.shopapp.repository.dao;

import com.ilyaevteev.shopapp.model.Review;
import com.ilyaevteev.shopapp.repository.ReviewRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ReviewRepositoryImpl implements ReviewRepository {
    @Autowired
    SessionFactory sessionFactory;

    @Override
    public void saveReview(Review review) {
        Session session = sessionFactory.getCurrentSession();
        session.save(review);
    }
}
