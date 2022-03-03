package com.tickets.booking.web.mappers;

import com.tickets.booking.BaseTestClass;
import com.tickets.booking.domain.FlightEntity;
import com.tickets.booking.domain.PassengerEntity;
import com.tickets.booking.domain.RouteEntity;
import com.tickets.booking.domain.TicketEntity;
import com.tickets.booking.repository.TicketsRepository;
import com.tickets.booking.web.model.FlightDto;
import com.tickets.booking.web.model.TicketDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class TicketMapperTest extends BaseTestClass {

    @Autowired
    TicketsRepository ticketsRepository;

    TicketEntity ticketEntity;

    TicketMapper ticketMapper = TicketMapper.TICKET_MAPPER_INSTANCE;

    UUID id = UUID.randomUUID();

    @BeforeEach
    public void setUp(){
        saveEntities();
        ticketEntity = ticketsRepository.findAll().get(0);
    }

    @Test
    void ticketDtoToTicket() {

        TicketDto ticketDto = new TicketDto();
        ticketDto.setId(ticketEntity.getId());
        ticketDto.setPassengerId(ticketEntity.getPassengerEntity().getId());
        ticketDto.setFlightId(ticketEntity.getFlightEntity().getId());
        ticketDto.setActive(true);

        TicketEntity ticket = ticketMapper.ticketDtoToTicket(ticketDto);
        ticket.setPassengerEntity(ticketEntity.getPassengerEntity());
        ticket.setFlightEntity(ticketEntity.getFlightEntity());

        assertEquals(ticket.getId(), ticketDto.getId());
        assertEquals(ticket.getPassengerEntity().getId(), ticketDto.getPassengerId());
        assertEquals(ticket.getFlightEntity().getId(), ticketDto.getFlightId());
        assertEquals(ticket.isActive(), ticketDto.isActive());
    }

    @Test
    void ticketToTicketDto() {

        TicketEntity ticketEntity = new TicketEntity();
        ticketEntity.setId(id);
        ticketEntity.setPassengerEntity(PassengerEntity.builder().build());
        ticketEntity.setFlightEntity(FlightEntity.builder().build());
        ticketEntity.setActive(true);

        TicketDto ticketDto = ticketMapper.ticketToTicketDto(ticketEntity);

        assertEquals(id, ticketDto.getId());
        assertEquals(ticketEntity.getPassengerEntity().getId(), ticketDto.getPassengerId());
        assertEquals(ticketEntity.getFlightEntity().getId(), ticketDto.getFlightId());
        assertEquals(ticketEntity.isActive(), ticketDto.isActive());
    }

    @Test
    void updateTicketFromDto() {

        TicketDto ticketDto = new TicketDto();
        ticketDto.setPassengerId(ticketEntity.getPassengerEntity().getId());
        ticketDto.setFlightId(ticketEntity.getFlightEntity().getId());
        ticketDto.setActive(true);

        TicketEntity ticket = new TicketEntity();
        ticket.setPassengerEntity(ticketEntity.getPassengerEntity());
        ticket.setFlightEntity(ticketEntity.getFlightEntity());

        ticketMapper.updateTicketFromDto(ticketDto, ticketEntity);

        assertEquals(ticket.getId(), ticketDto.getId());
        assertEquals(ticket.getPassengerEntity().getId(), ticketDto.getPassengerId());
        assertEquals(ticket.getFlightEntity().getId(), ticketDto.getFlightId());
        assertEquals(ticket.isActive(), ticketDto.isActive());
    }
}