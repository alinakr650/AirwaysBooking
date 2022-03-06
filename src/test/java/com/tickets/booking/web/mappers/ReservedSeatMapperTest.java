package com.tickets.booking.web.mappers;

import com.tickets.booking.BaseTestClass;
import com.tickets.booking.domain.FlightEntity;
import com.tickets.booking.domain.ReservedSeatEntity;
import com.tickets.booking.domain.TicketEntity;
import com.tickets.booking.repository.FlightRepository;
import com.tickets.booking.repository.ReservedSeatRepository;
import com.tickets.booking.repository.TicketsRepository;
import com.tickets.booking.web.model.ReservedSeatDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@ActiveProfiles("test")
class ReservedSeatMapperTest extends BaseTestClass {

    @Autowired
    ReservedSeatRepository reservedSeatRepository;

    @Autowired
    TicketsRepository ticketsRepository;

    @Autowired
    FlightRepository flightRepository;

    ReservedSeatEntity reservedSeatEntity;

    FlightEntity flightEntity;

    TicketEntity ticketEntity;

    ReservedSeatMapper reservedSeatMapper = ReservedSeatMapper.RESERVEDSEAT_MAPPER_INSTANCE;

    UUID id = UUID.randomUUID();

    @BeforeEach
    public void setUp() {
        saveEntities();
        flightEntity = flightRepository.findAll().get(0);
        ticketEntity = ticketsRepository.findAll().get(0);
        reservedSeatEntity = reservedSeatRepository.findReservedSeatByTicketId(ticketEntity.getId())
                .get();
    }

    @Test
    public void reservedSeatDtoToReservedSeat() {

        ReservedSeatDto reservedSeatDto = new ReservedSeatDto();
        reservedSeatDto.setSeatNumber(reservedSeatEntity.getSeatNumber());
        reservedSeatDto.setId(reservedSeatEntity.getId());
        reservedSeatDto.setTicketId(ticketEntity.getId());
        reservedSeatDto.setFlightId(flightEntity.getId());

        ReservedSeatEntity reservedSeat = reservedSeatMapper.reservedSeatDtoToReservedSeat(reservedSeatDto);

        assertEquals(reservedSeatDto.getId(), reservedSeat.getId());
        assertEquals(reservedSeatDto.getSeatNumber(), reservedSeat.getSeatNumber());
        assertEquals(reservedSeatDto.getTicketId(), reservedSeatEntity.getTicketEntity().getId());
        assertEquals(reservedSeatDto.getFlightId(), reservedSeatEntity.getFlightEntity().getId());

    }

    @Test
    void reservedSeatToReservedSeatDto() {

        ReservedSeatDto reservedSeatDto = reservedSeatMapper.reservedSeatToReservedSeatDto(reservedSeatEntity);

        assertEquals(reservedSeatDto.getId(), reservedSeatEntity.getId());
        assertEquals(reservedSeatDto.getSeatNumber(), reservedSeatEntity.getSeatNumber());
        assertThat(reservedSeatDto.getTicketId()).isNull();
        assertThat(reservedSeatDto.getFlightId()).isNull();
    }

    @Test
    public void updateReservedSeatFromDto() {

        ReservedSeatDto reservedSeatDto = new ReservedSeatDto();
        reservedSeatDto.setSeatNumber(reservedSeatEntity.getSeatNumber());
        reservedSeatDto.setId(reservedSeatEntity.getId());
        reservedSeatDto.setTicketId(null);
        reservedSeatDto.setFlightId(flightEntity.getId());

        reservedSeatMapper.updateReservedSeatFromDto(reservedSeatDto, reservedSeatEntity);

        assertEquals(reservedSeatDto.getId(), reservedSeatEntity.getId());
        assertEquals(reservedSeatDto.getSeatNumber(), reservedSeatEntity.getSeatNumber());
        assertThat(reservedSeatDto.getTicketId()).isNull();
        assertEquals(reservedSeatDto.getFlightId(), reservedSeatEntity.getFlightEntity().getId());
    }


}