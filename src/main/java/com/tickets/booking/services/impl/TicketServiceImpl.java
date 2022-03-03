package com.tickets.booking.services.impl;

import com.tickets.booking.domain.FlightEntity;
import com.tickets.booking.domain.PassengerEntity;
import com.tickets.booking.domain.TicketEntity;
import com.tickets.booking.repository.TicketsRepository;
import com.tickets.booking.services.FlightService;
import com.tickets.booking.services.PassengerService;
import com.tickets.booking.services.TicketService;
import com.tickets.booking.services.exceptions.FlightNotFoundException;
import com.tickets.booking.services.exceptions.TicketNotFoundException;
import com.tickets.booking.web.mappers.TicketMapper;
import com.tickets.booking.web.model.TicketRequisite;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class TicketServiceImpl implements TicketService {

    private final TicketsRepository ticketsRepository;
    private final PassengerService passengerService;
    private final FlightService flightService;

    public TicketServiceImpl(TicketsRepository ticketsRepository, TicketMapper ticketMapper,
                             PassengerService passengerService, FlightService flightService) {
        this.ticketsRepository = ticketsRepository;
        this.passengerService = passengerService;
        this.flightService = flightService;
    }

    @Override
    public Optional<TicketEntity> findById(UUID ticketId) {
        return ticketsRepository.findById(ticketId);
    }


    @Override
    public void deleteTicketById(UUID id) {
        if (id != null) {
            ticketsRepository.deleteById(id);
        } else {
            throw new TicketNotFoundException("Ticket Not Found Exception");
        }
    }

    @Override
    public void updateTicket(TicketRequisite ticketRequisite, UUID id) {
        if (ticketRequisite.getExistingTicketId() != id) {
            throw new TicketNotFoundException("Conflicting Path Variable And Ticket Requisite Ids");
        }
        TicketEntity ticketEntity = ticketsRepository.findById(id).orElseThrow(TicketNotFoundException::new);
        PassengerEntity passengerEntity = passengerService.findPassengerEntityById(ticketEntity.getPassengerEntity().getId());
        passengerService.updatePassengerByRequisites(ticketRequisite, passengerEntity);
        ticketEntity.setPassengerEntity(passengerEntity);
        ticketsRepository.save(ticketEntity);
    }


    @Override
    public TicketRequisite createNewTicketWithRequisites(TicketRequisite ticketRequisite) {
        PassengerEntity passengerEntity = passengerService.findPassengerByFirstNameAndLastNameAndPassportNumberAndNationality
                        (ticketRequisite.getPassengersFirstName(),
                                ticketRequisite.getPassengersLastName(), ticketRequisite.getPassengersPassportNumber(),
                                ticketRequisite.getPassengersNationality())
                .orElse(passengerService.savePassenger(ticketRequisite));
        FlightEntity flightEntity = flightService.findById(ticketRequisite.getFlightId())
                .orElseThrow(FlightNotFoundException::new);
        TicketEntity ticketEntity = TicketEntity.builder()
                .flightEntity(flightEntity)
                .passengerEntity(passengerEntity)
                .build();

        passengerService.savePassenger(passengerEntity);
        TicketEntity savedTicket = ticketsRepository.save(ticketEntity);
        ticketRequisite.setExistingTicketId(savedTicket.getId());
        return ticketRequisite;
    }

    @Override
    public int countAllTicketsById(UUID id) {
        return ticketsRepository.countAllTicketsById(id);
    }
}
