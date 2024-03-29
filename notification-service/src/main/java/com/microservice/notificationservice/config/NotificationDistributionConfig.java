package com.microservice.notificationservice.config;


import com.microservice.common.messaging.TicketNotification;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;

import java.util.function.Consumer;

@Configuration
public class NotificationDistributionConfig {


    @Bean
    public Consumer<TicketNotification> input() {
        return message -> {
            System.out.println("---------------------------------------------------");
            System.out.println("Notification Received. Sending to Users.");
            System.out.println("Notification: " + message.toString());
        };
    }

}
