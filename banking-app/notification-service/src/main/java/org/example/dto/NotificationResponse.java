package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationResponse {
    private Long notificationId;
    private String resolvedContent;
    private String emailId;
    private Long accountNumber;
    private Timestamp sendTime;
    private String locale;
    private String templateName;

}
