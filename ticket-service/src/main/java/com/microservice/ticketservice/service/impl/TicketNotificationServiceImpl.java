package com.microservice.ticketservice.service.impl;

import com.microservice.common.messaging.TicketNotification;
import com.microservice.ticketservice.model.Ticket;
import com.microservice.ticketservice.service.TicketNotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TicketNotificationServiceImpl implements TicketNotificationService {

    private final StreamBridge streamBridge;

    @Override
    public void sendToQueue(Ticket ticket) {

        TicketNotification ticketNotification =new TicketNotification();
        ticketNotification.setAccountId(ticket.getAssignee());
        ticketNotification.setTicketId(ticket.getId());
        ticketNotification.setTicketDescription(ticket.getDescription());

        streamBridge.send("output-out-0", MessageBuilder.withPayload(ticketNotification).build());
    }


}
