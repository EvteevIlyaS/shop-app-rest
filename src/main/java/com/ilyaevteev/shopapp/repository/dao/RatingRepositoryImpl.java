package com.ilyaevteev.shopapp.repository.dao;

import com.ilyaevteev.shopapp.model.Rating;
import com.ilyaevteev.shopapp.repository.RatingRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RatingRepositoryImpl implements RatingRepository {
    @Autowired
    SessionFactory sessionFactory;

    @Override
    public void saveRating(Rating rating) {
        Session session = sessionFactory.getCurrentSession();
        session.save(rating);
    }
}
