package com.microservice.notificationservice;

import com.microservice.common.messaging.TicketNotification;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Service
public class NotificationDistributionService {
    @Bean
    public Consumer<String> input() {
        return message -> {
            System.out.println("---------------------------------------------------");
            System.out.println("Notification Received. Sending to Users.");
            System.out.println("Notification: " + message.toString());
        };
    }
}
