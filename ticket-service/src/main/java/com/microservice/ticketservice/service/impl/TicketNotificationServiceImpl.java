package com.microservice.ticketservice.service.impl;

import com.microservice.ticketservice.model.Ticket;
import com.microservice.ticketservice.service.TicketNotificationService;
import org.springframework.stereotype.Service;

@Service
public class TicketNotificationServiceImpl implements TicketNotificationService {
    @Override
    public void sendToQueue(Ticket ticket) {

    }
}
