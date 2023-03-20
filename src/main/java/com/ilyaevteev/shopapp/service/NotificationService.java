package com.ilyaevteev.shopapp.service;


import com.ilyaevteev.shopapp.model.Notification;

public interface NotificationService {
    Notification sendNotification(Notification notification);
}
