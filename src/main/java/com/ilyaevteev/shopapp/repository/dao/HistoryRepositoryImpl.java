package com.ilyaevteev.shopapp.repository.dao;

import com.ilyaevteev.shopapp.model.History;
import com.ilyaevteev.shopapp.repository.HistoryRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class HistoryRepositoryImpl implements HistoryRepository {
    @Autowired
    SessionFactory sessionFactory;
    @Override
    public void saveHistory(History history) {
        Session session = sessionFactory.getCurrentSession();
        session.save(history);
    }

    @Override
    public History findById(Long id) {
        Session session = sessionFactory.getCurrentSession();

        return session.get(History.class, id);
    }

    @Override
    public void deleteById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Query<History> query = session.createQuery("delete from History" +
                " where id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }
}
