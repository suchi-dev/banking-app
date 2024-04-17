package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.NotificationRequest;
import org.example.dto.NotificationResponse;
import org.example.dto.TemplateRequest;
import org.example.dto.TemplateResponse;
import org.example.service.NotificationService;
import org.example.service.TemplateService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

   private final NotificationService notificationService;

    @PostMapping
    @ResponseBody
    public NotificationResponse sendNotification(@RequestBody NotificationRequest notificationRequest){
        return notificationService.sendNotification(notificationRequest);
    }

    @GetMapping
    @ResponseBody
    public List<NotificationResponse> getAllNotifications(){
        return notificationService.getAllNotifications();
    }

    @GetMapping("/{Id}")
    @ResponseBody
    public NotificationResponse getByNameAndLocale(@PathVariable Long Id){
        return notificationService.getNotificationById(Id);
    }


}
