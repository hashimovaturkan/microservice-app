package com.microservice.ticketservice.service;

import com.microservice.ticketservice.model.Ticket;


public interface TicketNotificationService {

    void sendToQueue(Ticket ticket);
}
