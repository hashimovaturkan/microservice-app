package com.microservice.ticketservice.service.impl;

import com.microservice.ticketservice.dto.TicketDto;
import com.microservice.ticketservice.repository.TicketRepository;
import com.microservice.ticketservice.repository.elasticSearch.TicketElasticRepository;
import com.microservice.ticketservice.service.TicketNotificationService;
import com.microservice.ticketservice.service.TicketService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TicketServiceImpl implements TicketService {

    //private final TicketElasticRepository ticketElasticRepository;
    private final TicketRepository ticketRepository;
    private final TicketNotificationService ticketNotificationService;
    //private final AccountServiceClient accountServiceClient;

    public TicketServiceImpl(TicketRepository ticketRepository, TicketNotificationService ticketNotificationService) {
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
