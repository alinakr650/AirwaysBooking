package com.tickets.booking.services;

import com.tickets.booking.domain.ReservedSeatEntity;
import com.tickets.booking.web.model.FlightDto;
import com.tickets.booking.web.model.ReservedSeatDto;
import com.tickets.booking.web.model.TicketDto;
import com.tickets.booking.web.model.TicketRequisite;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Transactional
public interface ReservedSeatService {

    String findRandomAvailableSeat(UUID flightId);

    ReservedSeatDto reserveASeat(UUID ticketId, UUID flightId, String seatNumber);

    List<String> displayAllAvailableSeats(UUID flightId);

    void saveReservedSeat(ReservedSeatEntity reservedSeatEntity);

    ReservedSeatDto saveReservedSeatByDto(ReservedSeatDto reservedSeatDto);

    void deleteReservedSeatById(UUID id);

    ReservedSeatDto changeSeat(UUID ticketId, UUID flightId, String newSeat);
}
