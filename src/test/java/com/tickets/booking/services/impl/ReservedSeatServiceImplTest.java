package com.tickets.booking.services.impl;

import com.tickets.booking.BaseTestClass;
import com.tickets.booking.domain.FlightEntity;
import com.tickets.booking.domain.ReservedSeatEntity;
import com.tickets.booking.domain.TicketEntity;
import com.tickets.booking.services.ReservedSeatService;
import com.tickets.booking.web.mappers.FlightMapper;
import com.tickets.booking.web.mappers.ReservedSeatMapper;
import com.tickets.booking.web.model.ReservedSeatDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class ReservedSeatServiceImplTest extends BaseTestClass {

    @Autowired
    ReservedSeatMapper reservedSeatMapper;

    @Autowired
    FlightMapper flightMapper;

    @Autowired
    ReservedSeatService reservedSeatService;

    @BeforeEach
    public void setUp() throws Exception {
        super.saveEntities();
    }

    @Test
    void displayAllAvailableSeats() {

        FlightEntity flightEntity = super.flightRepository.findAll().get(0);
        UUID flightId = flightEntity.getId();
        List<ReservedSeatEntity> reservedSeatEntityList = reservedSeatRepository.findAllByFlightEntityId(flightEntity.getId());
        List<String> occupiedSeats = reservedSeatRepository.findAllByFlightEntityId(flightId)
                .stream()
                .map(ReservedSeatEntity::getSeatNumber)
                .collect(Collectors.toList());
        List<String> availableSeats = reservedSeatService.displayAllAvailableSeats(flightId);

        assertThat(availableSeats).isNotEmpty();
        assertThat(availableSeats).doesNotContain("A9");
        assertThat(availableSeats.size() - occupiedSeats.size()).isLessThan(availableSeats.size());
        assertThat(reservedSeatEntityList).isNotEmpty();
    }

    @Test
    void reserveASeat() {
        TicketEntity ticketEntity = super.ticketsRepository.findAll().get(0);
        FlightEntity flightEntity = super.flightRepository.findAll().get(0);

        UUID ticketId = ticketEntity.getId();
        UUID flightId = flightEntity.getId();

        ReservedSeatDto reservedSeatDto = reservedSeatService.reserveASeat(ticketId, flightId, "A6");

        assertThat(reservedSeatDto.getFlightId()).isEqualTo(flightEntity.getId());
        assertThat(reservedSeatDto.getTicketId()).isEqualTo(ticketEntity.getId());
        assertThat(reservedSeatDto.getSeatNumber()).isEqualTo("A6");
    }

    @Test
    void findRandomAvailableSeat() {
        FlightEntity flightEntity = super.flightRepository.findAll().get(0);
        UUID flightId = flightEntity.getId();

        String randomAvailableSeat = reservedSeatService.findRandomAvailableSeat(flightId);

        assertThat(randomAvailableSeat).isNotEmpty();

    }
}