package com.microservice.ticketservice.configuration;


import com.microservice.common.messaging.TicketNotification;
import com.microservice.ticketservice.model.Ticket;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;

import java.util.function.Function;

@Configuration
public class TicketDistributionConfig {

//    @Bean
//    public Function<Ticket, Message<TicketNotification>> output() {
//        return ticket -> {
//            TicketNotification ticketNotification = new TicketNotification();
//            ticketNotification.setAccountId(ticket.getAssignee());
//            ticketNotification.setTicketId(ticket.getId());
//            ticketNotification.setTicketDescription(ticket.getDescription());
//            return MessageBuilder.withPayload(ticketNotification).build();
//        };
//    }


}
