package com.tickets.booking.services.impl;

import com.tickets.booking.BaseTestClass;
import com.tickets.booking.domain.TicketEntity;
import com.tickets.booking.domain.nationalities.Nationality;
import com.tickets.booking.services.TicketService;
import com.tickets.booking.web.mappers.TicketMapper;
import com.tickets.booking.web.model.TicketDto;
import com.tickets.booking.web.model.TicketRequisite;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class TicketServiceImplTest extends BaseTestClass {

    @Autowired
    TicketService ticketService;

    @Autowired
    TicketMapper ticketMapper;

    @BeforeEach
    void setUp() {
        super.saveEntities();
    }

    @Test
    void findById() {
        TicketEntity ticketEntity = ticketsRepository.findAll().get(0);
        TicketDto ticketDto = ticketMapper.ticketToTicketDto(ticketService.findById(ticketEntity.getId()).get());

        assertThat(ticketDto.getId()).isEqualTo(ticketEntity.getId());
    }

    @Test
    void deleteTicketById() {
        TicketEntity ticketEntity = ticketsRepository.findAll().get(0);
        ticketService.deleteTicketById(ticketEntity.getId());

        assertThat(ticketsRepository.findById(ticketEntity.getId())).isEmpty();
    }

    @Test
    void updateTicket() {
        UUID ticketEntityId = ticketsRepository.findAll().get(0).getId();

        TicketRequisite ticketRequisite = new TicketRequisite();
        ticketRequisite.setExistingTicketId(ticketEntityId);
        ticketRequisite.setFlightId(flightRepository.findAll().get(0).getId());
        ticketRequisite.setPassengersFirstName("Mycroft");
        ticketRequisite.setPassengersMiddleName(null);
        ticketRequisite.setPassengersLastName("Holmes");
        ticketRequisite.setPassengersPassportNumber(passengerRepository.findAll().get(0).getPassportNumber());
        ticketRequisite.setPassengersNationality(Nationality.UK);

        ticketService.updateTicket(ticketRequisite, ticketEntityId);

        TicketEntity updatedEntity = ticketsRepository.findById(ticketEntityId).get();
        assertThat(updatedEntity.getPassengerEntity().getFirstName()).isEqualTo("Mycroft");
    }

    @Test
    void createNewTicketWithRequisite() {
        TicketRequisite savedTicketRequisite = ticketService.createNewTicketWithRequisites(returnValidTicketRequisite());

        assertThat(savedTicketRequisite.getExistingTicketId()).isNotNull();
        assertThat(savedTicketRequisite.getFlightId()).isEqualTo(flightRepository.findAll().get(0).getId());
        assertThat(savedTicketRequisite.getPassengersPassportNumber()).isEqualTo(passengerRepository.findAll().get(0).getPassportNumber());
    }

    private TicketDto returnValidDto() {
        TicketDto ticketDto = new TicketDto();
        ticketDto.setFlightId(flightRepository.findAll().get(0).getId());
        ticketDto.setPassengerId(passengerRepository.findAll().get(0).getId());
        ticketDto.setActive(true);
        return ticketDto;
    }

    private TicketRequisite returnValidTicketRequisite() {
        TicketRequisite ticketRequisite = new TicketRequisite();
        ticketRequisite.setFlightId(flightRepository.findAll().get(0).getId());
        ticketRequisite.setPassengersFirstName(passengerRepository.findAll().get(0).getFirstName());
        ticketRequisite.setPassengersMiddleName(passengerRepository.findAll().get(0).getMiddleName());
        ticketRequisite.setPassengersLastName(passengerRepository.findAll().get(0).getLastName());
        ticketRequisite.setPassengersPassportNumber(passengerRepository.findAll().get(0).getPassportNumber());
        ticketRequisite.setPassengersNationality(Nationality.Albania);
        return ticketRequisite;
    }

}