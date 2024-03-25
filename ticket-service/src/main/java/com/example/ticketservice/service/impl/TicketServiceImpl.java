package com.example.ticketservice.service.impl;

import com.example.ticketservice.dto.TicketDto;
import com.example.ticketservice.model.PriorityType;
import com.example.ticketservice.model.Ticket;
import com.example.ticketservice.model.TicketStatus;
import com.example.ticketservice.model.elasticSearch.TicketModel;
import com.example.ticketservice.repository.TicketRepository;
import com.example.ticketservice.repository.elasticSearch.TicketElasticRepository;
import com.example.ticketservice.service.TicketNotificationService;
import com.example.ticketservice.service.TicketService;
import lombok.RequiredArgsConstructor;
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
//        // Ticket Entity
//        if (ticketDto.getDescription() == null)
//            throw new IllegalArgumentException("Description bos olamaz");
//
//        Ticket ticket = new Ticket();
//        ResponseEntity<AccountDto> accountDtoResponseEntity = accountServiceClient.get(ticketDto.getAssignee());
//
//        ticket.setDescription(ticketDto.getDescription());
//        ticket.setNotes(ticketDto.getNotes());
//        ticket.setTicketDate(ticketDto.getTicketDate());
//        ticket.setTicketStatus(TicketStatus.valueOf(ticketDto.getTicketStatus()));
//        ticket.setPriorityType(PriorityType.valueOf(ticketDto.getPriorityType()));
//        ticket.setAssignee(accountDtoResponseEntity.getBody().getId());
//
//        // mysql kaydet
//        ticket = ticketRepository.save(ticket);
//
//
//        // TicketModel nesnesi yarat
//        TicketModel model = TicketModel.builder()
//                .description(ticket.getDescription())
//                .notes(ticket.getNotes())
//                .id(ticket.getId())
//                .assignee(accountDtoResponseEntity.getBody().getNameSurname())
//                .priorityType(ticket.getPriorityType().getLabel())
//                .ticketStatus(ticket.getTicketStatus().getLabel())
//                .ticketDate(ticket.getTicketDate()).build();
//
//        // elastic kaydet
//        ticketElasticRepository.save(model);
//
//        // olusan nesneyi döndür
//        ticketDto.setId(ticket.getId());
//
//        // Kuyruga notification yaz
//        ticketNotificationService.sendToQueue(ticket);
//        return ticketDto;
        return null;
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
