package com.microservice.ticketservice.service;

import com.microservice.common.messaging.TicketNotification;
import com.microservice.ticketservice.model.Ticket;
import org.springframework.messaging.Message;

import java.util.function.Function;


public interface TicketNotificationService {

    void sendToQueue(Ticket ticket);
}
