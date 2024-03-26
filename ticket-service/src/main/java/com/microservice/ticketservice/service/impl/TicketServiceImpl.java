package com.microservice.ticketservice.service.impl;

import com.microservice.ticketservice.dto.TicketDto;
import com.microservice.ticketservice.model.PriorityType;
import com.microservice.ticketservice.model.Ticket;
import com.microservice.ticketservice.model.TicketStatus;
import com.microservice.ticketservice.model.elasticSearch.TicketModel;
import com.microservice.ticketservice.repository.TicketRepository;
import com.microservice.ticketservice.repository.elasticSearch.TicketElasticRepository;
import com.microservice.ticketservice.service.TicketNotificationService;
import com.microservice.ticketservice.service.TicketService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TicketServiceImpl implements TicketService {

    private final TicketElasticRepository ticketElasticRepository;
    private final TicketRepository ticketRepository;
    private final TicketNotificationService ticketNotificationService;
    //private final AccountServiceClient accountServiceClient;

    public TicketServiceImpl(TicketElasticRepository ticketElasticRepository, TicketRepository ticketRepository, TicketNotificationService ticketNotificationService) {
        this.ticketElasticRepository = ticketElasticRepository;
        this.ticketRepository = ticketRepository;
        this.ticketNotificationService = ticketNotificationService;
    }

    @Override
    @Transactional
    public TicketDto save(TicketDto ticketDto) {

        if (ticketDto.getDescription() == null)
            throw new IllegalArgumentException("Description should not be empty");

        Ticket ticket = new Ticket();
        //ResponseEntity<AccountDto> accountDtoResponseEntity = accountServiceClient.get(ticketDto.getAssignee());

        ticket.setDescription(ticketDto.getDescription());
        ticket.setNotes(ticketDto.getNotes());
        ticket.setTicketDate(ticketDto.getTicketDate());
        ticket.setTicketStatus(TicketStatus.valueOf(ticketDto.getTicketStatus()));
        ticket.setPriorityType(PriorityType.valueOf(ticketDto.getPriorityType()));
        //ticket.setAssignee(accountDtoResponseEntity.getBody().getId());

        // save mysql
        ticket = ticketRepository.save(ticket);


        // create TicketModel
        TicketModel model = TicketModel.builder()
                .description(ticket.getDescription())
                .notes(ticket.getNotes())
                .id(ticket.getId())
                //.assignee(accountDtoResponseEntity.getBody().getNameSurname())
                .priorityType(ticket.getPriorityType().getLabel())
                .ticketStatus(ticket.getTicketStatus().getLabel())
                .ticketDate(ticket.getTicketDate()).build();

        // save elastic
        ticketElasticRepository.save(model);

        ticketDto.setId(ticket.getId());

        // Queue notofication
        ticketNotificationService.sendToQueue(ticket);
        return ticketDto;
    }

    @Override
    public TicketDto update(String id, TicketDto ticketDto) {
        return null;
    }

    @Override
    public TicketDto getById(String ticketId) {
        return null;
    }

    @Override
    public Page<TicketDto> getPagination(Pageable pageable) {
        return null;
    }
}
