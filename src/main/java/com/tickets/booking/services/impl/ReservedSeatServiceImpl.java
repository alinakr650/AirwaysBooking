package com.tickets.booking.services.impl;

import com.tickets.booking.domain.FlightEntity;
import com.tickets.booking.domain.PassengerEntity;
import com.tickets.booking.domain.ReservedSeatEntity;
import com.tickets.booking.domain.TicketEntity;
import com.tickets.booking.domain.planes.Plane;
import com.tickets.booking.repository.ReservedSeatRepository;
import com.tickets.booking.services.FlightService;
import com.tickets.booking.services.ReservedSeatService;
import com.tickets.booking.services.TicketService;
import com.tickets.booking.services.exceptions.FlightNotFoundException;
import com.tickets.booking.services.exceptions.SeatNotAvailableException;
import com.tickets.booking.services.exceptions.TicketNotFoundException;
import com.tickets.booking.services.util.PlanePicker;
import com.tickets.booking.web.mappers.FlightMapper;
import com.tickets.booking.web.mappers.ReservedSeatMapper;
import com.tickets.booking.web.mappers.TicketMapper;
import com.tickets.booking.web.model.FlightDto;
import com.tickets.booking.web.model.ReservedSeatDto;
import com.tickets.booking.web.model.TicketDto;
import com.tickets.booking.web.model.TicketRequisite;
import lombok.Synchronized;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.persistence.NonUniqueResultException;
import java.security.SecureRandom;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReservedSeatServiceImpl implements ReservedSeatService {

    private final ReservedSeatRepository reservedSeatRepository;
    private final FlightService flightService;
    private final TicketService ticketService;
    private final FlightMapper flightMapper;
    private final ReservedSeatMapper reservedSeatMapper;

    SecureRandom random = new SecureRandom();

    public ReservedSeatServiceImpl(ReservedSeatRepository reservedSeatRepository, FlightService flightService, TicketService ticketService, FlightMapper flightMapper, ReservedSeatMapper reservedSeatMapper) {
        this.reservedSeatRepository = reservedSeatRepository;
        this.flightService = flightService;
        this.ticketService = ticketService;
        this.flightMapper = flightMapper;
        this.reservedSeatMapper = reservedSeatMapper;
    }

    @Synchronized
    @Override
    public String findRandomAvailableSeat(UUID flightId) {
        FlightDto flightDto = flightMapper.flightToFlightDto(flightService.findById(flightId)
                .orElseThrow(() -> new FlightNotFoundException("Flight Not Found")));
        List<String> availableSeats = displayAllAvailableSeats(flightId);

        if (availableSeats.isEmpty()) {
            throw new SeatNotAvailableException("The seat is not available");
        } else {
            return availableSeats.get(random.nextInt(availableSeats.size()));
        }
    }

    @Synchronized
    @Override
    public List<String> displayAllAvailableSeats(UUID flightId) {
        FlightDto flightDto = flightMapper.flightToFlightDto(flightService.findById(flightId)
                .orElseThrow(() -> new FlightNotFoundException("Flight Not Found")));

        String planeType = flightDto.getPlaneName();
        Plane plane = PlanePicker.getPlaneByName(planeType);
        List<String> allSeats = plane.getAllSeatsNames();

        List<String> occupiedSeats = reservedSeatRepository.findAllByFlightEntityId(flightDto.getId())
                .stream()
                .map(ReservedSeatEntity::getSeatNumber)
                .collect(Collectors.toList());

        return allSeats
                .stream()
                .filter(element -> !occupiedSeats.contains(element))
                .collect(Collectors.toList());
    }

    @Override
    @Synchronized
    public ReservedSeatDto reserveASeat(UUID ticketId, UUID flightId, String seatNumber) {
        TicketEntity ticketEntity = ticketService.findById(ticketId)
                .orElseThrow(() -> new TicketNotFoundException("Ticket Not Found"));
        FlightEntity flightEntity = flightService.findById(flightId)
                .orElseThrow(() -> new FlightNotFoundException("Flight Not Found"));

        List<String> allAvailableSeats = displayAllAvailableSeats(flightId);

        if (allAvailableSeats.contains(seatNumber)) {
            ReservedSeatEntity reservedSeatEntity = new ReservedSeatEntity();
            reservedSeatEntity.setSeatNumber(seatNumber);
            reservedSeatEntity.setTicketEntity(ticketEntity);
            reservedSeatEntity.setFlightEntity(flightEntity);
            ReservedSeatDto reservedSeatDto = reservedSeatMapper.reservedSeatToReservedSeatDto(reservedSeatEntity);
            reservedSeatDto.setFlightId(flightId);
            reservedSeatDto.setTicketId(ticketId);
            return reservedSeatDto;
        } else {
            throw new SeatNotAvailableException("The seat is not available");
        }
    }

    @Override
    @Synchronized
    public ReservedSeatDto changeSeat(UUID ticketId, UUID flightId, String newSeat) {
        TicketEntity ticketEntity = ticketService.findById(flightId)
                .orElseThrow(() -> new TicketNotFoundException("Ticket Not Found"));
        FlightEntity flightEntity = flightService.findById(flightId)
                .orElseThrow(() -> new FlightNotFoundException("Flight Not Found"));

        ReservedSeatEntity reservedSeatEntity = reservedSeatRepository.findReservedSeatByTicketId(ticketId)
                .orElseThrow(EntityNotFoundException::new);

        List<String> allAvailableSeats = displayAllAvailableSeats(flightId);

        if (allAvailableSeats.contains(newSeat)) {
            deleteReservedSeatById(reservedSeatEntity.getId());
            ReservedSeatEntity newReservedSeatEntity = new ReservedSeatEntity();
            newReservedSeatEntity.setSeatNumber(newSeat);
            newReservedSeatEntity.setTicketEntity(ticketEntity);
            newReservedSeatEntity.setFlightEntity(flightEntity);
            saveReservedSeat(newReservedSeatEntity);
            ReservedSeatDto reservedSeatDto = reservedSeatMapper.reservedSeatToReservedSeatDto(newReservedSeatEntity);
            reservedSeatDto.setFlightId(newReservedSeatEntity.getFlightEntity().getId());
            reservedSeatDto.setTicketId(newReservedSeatEntity.getTicketEntity().getId());
            return reservedSeatDto;
        } else {
            throw new SeatNotAvailableException("The seat is not available");
        }
    }

    @Override
    public void saveReservedSeat(ReservedSeatEntity reservedSeatEntity) {
        if (reservedSeatEntity != null) {
            reservedSeatRepository.save(reservedSeatEntity);
        } else {
            throw new EntityNotFoundException();
        }
    }

    @Override
    public ReservedSeatDto saveReservedSeatByDto(ReservedSeatDto reservedSeatDto) {
        if (reservedSeatDto != null) {
            ReservedSeatEntity reservedSeatEntity = reservedSeatMapper.reservedSeatDtoToReservedSeat(reservedSeatDto);
            reservedSeatEntity.setTicketEntity(ticketService.findById(reservedSeatDto.getTicketId())
                    .orElseThrow(TicketNotFoundException::new));
            reservedSeatEntity.setFlightEntity(flightService.findById(reservedSeatDto.getFlightId())
                    .orElseThrow(FlightNotFoundException::new));
            if (ticketService.countAllTicketsById(reservedSeatEntity.getTicketEntity().getId()) == 1) {
                reservedSeatDto = reservedSeatMapper.reservedSeatToReservedSeatDto(reservedSeatRepository.save(reservedSeatEntity));
                reservedSeatDto.setTicketId(reservedSeatEntity.getTicketEntity().getId());
                reservedSeatDto.setFlightId(reservedSeatEntity.getFlightEntity().getId());
                return reservedSeatDto;
            } else {
                throw new NonUniqueResultException("Ticket With This Id Is Already Registered");
            }
        } else {
            throw new EntityNotFoundException();
        }
    }

    @Override
    public void deleteReservedSeatById(UUID id) {
        if (id != null) {
            reservedSeatRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException();
        }
    }

}
