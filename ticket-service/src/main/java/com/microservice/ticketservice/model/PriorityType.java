package com.microservice.ticketservice.model;

import lombok.Getter;

@Getter
public enum PriorityType {

    URGENT("Urgent"),
    LOW("Low"),
    HIGH("High");

    private String label;

    PriorityType(String label){
        this.label = label;
    }

}
