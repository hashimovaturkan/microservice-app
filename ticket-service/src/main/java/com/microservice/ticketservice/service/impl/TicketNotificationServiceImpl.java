package com.microservice.ticketservice.service.impl;

//import com.microservice.common.messaging.TicketNotification;
//import com.microservice.ticketservice.model.Ticket;
//import com.microservice.ticketservice.service.TicketNotificationService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.cloud.stream.annotation.EnableBinding;
//import org.springframework.cloud.stream.messaging.Source;
//import org.springframework.messaging.support.MessageBuilder;
//import org.springframework.stereotype.Service;
//
//import java.util.function.Function;
//
//@Service
//@EnableBinding(Source.class)
//@RequiredArgsConstructor
//public class TicketNotificationServiceImpl implements TicketNotificationService {
//
//    private final Source source;
//
//    @Override
//    public void sendToQueue(Ticket ticket) {
//
//        TicketNotification ticketNotification =new TicketNotification();
//        ticketNotification.setAccountId(ticket.getAssignee());
//        ticketNotification.setTicketId(ticket.getId());
//        ticketNotification.setTicketDescription(ticket.getDescription());
//
//        source.output().send(MessageBuilder.withPayload(ticketNotification).build());
//    }
//
//
//}
