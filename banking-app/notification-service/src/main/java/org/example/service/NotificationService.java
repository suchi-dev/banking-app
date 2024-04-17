package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.NotificationRequest;
import org.example.dto.NotificationResponse;
import org.example.model.Notification;
import org.example.model.Template;
import org.example.repository.NotificationRepository;
import org.example.repository.TemplateRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final TemplateRepository templateRepository;
    private final NotificationRepository notificationRepository;

    public NotificationResponse sendNotification(NotificationRequest notificationRequest) {

        Template template = templateRepository.findByTemplateNameAndLocale(notificationRequest.getTemplateName(), notificationRequest.getLocale());

        String rawContent = template.getContent();
        HashMap<String, String> fields = notificationRequest.getFields();

        String resolvedContent = rawContent;

        for (Map.Entry<String,String> field: fields.entrySet()){
            resolvedContent = resolvedContent.replaceAll("%" + field.getKey() + "%",
                    field.getValue());
        }

        System.out.println(resolvedContent);

        Notification notification = Notification.builder()
                .resolvedContent(resolvedContent)
                .emailId(notificationRequest.getEmailId())
                .accountNumber(notificationRequest.getAccountNumber())
                .locale(notificationRequest.getLocale())
                .templateName(notificationRequest.getTemplateName())
                .sendTime(new Timestamp((new Date()).getTime()))
                .build();
        notificationRepository.save(notification);

        return mapToNotificationResponse(notification);
    }

    private NotificationResponse mapToNotificationResponse(Notification notification) {
        return NotificationResponse.builder()
                .notificationId(notification.getNotificationId())
                .sendTime(notification.getSendTime())
                .emailId(notification.getEmailId())
                .templateName(notification.getTemplateName())
                .locale(notification.getLocale())
                .accountNumber(notification.getAccountNumber())
                .resolvedContent(notification.getResolvedContent())
                .build();
    }

    public NotificationResponse getNotificationById (Long notificationId){
        return mapToNotificationResponse(notificationRepository.findById(notificationId)
                .get());
    }


    public List<NotificationResponse> getAllNotifications (){
        return notificationRepository.findAll().stream()
                .map(this::mapToNotificationResponse)
                .toList();
    }

}
