package com.ilyaevteev.shopapp.service.impl;

import com.ilyaevteev.shopapp.model.Notification;
import com.ilyaevteev.shopapp.repository.NotificationRepository;
import com.ilyaevteev.shopapp.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class NotificationServiceImpl implements NotificationService {
    @Autowired
    NotificationRepository notificationRepository;

    @Override
    @Transactional
    public Notification sendNotification(Notification notification) {
        return notificationRepository.save(notification);
    }
}
