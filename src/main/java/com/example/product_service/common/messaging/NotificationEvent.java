package com.example.product_service.common.messaging;

import java.io.Serializable;
import java.util.Map;

public record NotificationEvent(
        String recipientEmail,
        NotificationType type,
        Map<String, String> templateData
) implements Serializable {
}

