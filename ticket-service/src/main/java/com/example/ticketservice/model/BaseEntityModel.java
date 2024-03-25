package com.example.ticketservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import org.springframework.data.annotation.CreatedDate;

import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
public abstract class BaseEntityModel implements Serializable {

    @CreatedDate
    @Column(name = "created_at")
    private Date cretedAt;

    @Column(name = "updated_at")
    private Date updatedAt;
}
