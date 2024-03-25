package com.example.ticketservice.service;

import com.example.ticketservice.model.Ticket;


public interface TicketNotificationService {

    void sendToQueue(Ticket ticket);
}
