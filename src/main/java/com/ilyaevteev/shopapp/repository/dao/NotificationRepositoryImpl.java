package com.ilyaevteev.shopapp.repository.dao;

import com.ilyaevteev.shopapp.model.Notification;
import com.ilyaevteev.shopapp.repository.NotificationRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class NotificationRepositoryImpl implements NotificationRepository {
    @Autowired
    SessionFactory sessionFactory;

    @Override
    public Notification save(Notification notification) {
        Session session = sessionFactory.getCurrentSession();
        session.save(notification);

        return notification;
    }
}
