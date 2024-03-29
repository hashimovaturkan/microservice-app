package com.microservice.ticketservice.service.impl;

import com.microservice.common.messaging.TicketNotification;
import com.microservice.ticketservice.model.Ticket;
import com.microservice.ticketservice.service.TicketNotificationService;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class TicketNotificationServiceImpl implements TicketNotificationService {

    @Bean
    public Function<Ticket, Message<TicketNotification>> notification() {
        return ticket -> {
            TicketNotification ticketNotification = new TicketNotification();
            ticketNotification.setAccountId(ticket.getAssignee());
            ticketNotification.setTicketId(ticket.getId());
            ticketNotification.setTicketDescription(ticket.getDescription());
            return MessageBuilder.withPayload(ticketNotification).build();
        };
    }

}
