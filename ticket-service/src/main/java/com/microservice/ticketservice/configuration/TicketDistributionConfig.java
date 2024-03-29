package com.microservice.ticketservice.configuration;

import com.microservice.common.messaging.TicketNotification;
import com.microservice.ticketservice.model.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;
import java.util.function.Function;

import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.messaging.Message;

@Configuration
public class TicketDistributionConfig {

    @Bean
    public Function<Ticket, Message<TicketNotification>> output() {
        return ticket -> {
            TicketNotification ticketNotification = new TicketNotification();
            ticketNotification.setAccountId(ticket.getAssignee());
            ticketNotification.setTicketId(ticket.getId());
            ticketNotification.setTicketDescription(ticket.getDescription());
            return MessageBuilder.withPayload(ticketNotification).build();
        };
    }


}
