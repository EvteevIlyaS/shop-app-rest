package com.ilyaevteev.shopapp.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.ilyaevteev.shopapp.model.Notification;
import com.ilyaevteev.shopapp.model.tools.CustomJsonDateDeserializer;
import lombok.Data;

import java.util.Date;

@Data
public class NotificationDto {
    private Long id;

    private String header;

    @JsonDeserialize(using = CustomJsonDateDeserializer.class)
    private Date date;

    private String text;

    private Long user;

    public static NotificationDto fromNotification(Notification notification) {
        NotificationDto notificationDto = new NotificationDto();
        notificationDto.setDate(notification.getDate());
        notificationDto.setId(notification.getId());
        notificationDto.setUser(notification.getUser().getId());
        notificationDto.setText(notification.getText());
        notificationDto.setHeader(notification.getHeader());

        return notificationDto;
    }
}
