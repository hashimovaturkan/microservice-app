package com.microservice.ticketservice.model;

import lombok.Getter;

@Getter
public enum TicketStatus {

    OPEN("Open"),
    IN_PROGRESS("In progress"),
    RESOLVED("Resolved"),
    CLOSED("Closed");

    private String label;

    TicketStatus(String label){
        this.label = label;
    }
}
