package com.tickets.booking.web.controller;

import com.tickets.booking.domain.PassengerEntity;
import com.tickets.booking.domain.TicketEntity;
import com.tickets.booking.domain.security.Role;
import com.tickets.booking.domain.security.User;
import com.tickets.booking.security.permissions.SeatChangePermission;
import com.tickets.booking.security.permissions.SeatDisplayAllPermission;
import com.tickets.booking.security.permissions.SeatReservePermission;
import com.tickets.booking.services.PassengerService;
import com.tickets.booking.services.ReservedSeatService;
import com.tickets.booking.services.TicketService;
import com.tickets.booking.services.exceptions.TicketNotFoundException;
import com.tickets.booking.services.exceptions.UserNotFoundException;
import com.tickets.booking.web.model.ReservedSeatDto;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;


@RestController
public class ReservedSeatController {

    private final ReservedSeatService reservedSeatService;
    private final TicketService ticketService;
    private final PassengerService passengerService;
    private Optional<PassengerEntity> passenger;
    private Set<String> roles;

    public ReservedSeatController(ReservedSeatService reservedSeatService, TicketService ticketService, PassengerService passengerService) {
        this.reservedSeatService = reservedSeatService;
        this.ticketService = ticketService;
        this.passengerService = passengerService;
    }

    @SeatReservePermission
    @PostMapping({"/{flightId}/tickets/{ticketId}/seats"})
    @ResponseStatus(HttpStatus.CREATED)
    public ReservedSeatDto reserveSeat(@AuthenticationPrincipal User user, @PathVariable UUID flightId, @PathVariable UUID ticketId) throws IllegalAccessException {
        roles = user.getRoles().stream().map(Role::getName).collect(Collectors.toSet());

        if (roles.contains("ADMIN")) {
            String seatNumber = reservedSeatService.findRandomAvailableSeat(flightId);
            ReservedSeatDto reservedSeatDto = reservedSeatService.reserveASeat(ticketId, flightId, seatNumber);
            return reservedSeatService.saveReservedSeatByDto(reservedSeatDto);
        }
        if (user.getPassenger() != null) {
            passenger = passengerService.findPassengerEntityByIdOptional(user.getPassenger().getId());
            TicketEntity ticketEntity = ticketService.findById(ticketId)
                    .orElseThrow(TicketNotFoundException::new);

            if (passenger.isPresent() && passenger.get().getId() == ticketEntity.getPassengerEntity().getId()) {
                String seatNumber = reservedSeatService.findRandomAvailableSeat(flightId);
                ReservedSeatDto reservedSeatDto = reservedSeatService.reserveASeat(ticketId, flightId, seatNumber);
                return reservedSeatService.saveReservedSeatByDto(reservedSeatDto);
            } else throw new IllegalAccessException("Illegal Access");
        } else throw new UserNotFoundException("User Not Found");
    }

    @SeatDisplayAllPermission
    @GetMapping({"/{flightId}/available-seats"})
    @ResponseStatus(HttpStatus.OK)
    public List<String> displayAllSeats(@AuthenticationPrincipal User user, @PathVariable UUID flightId) {
        roles = user.getRoles().stream().map(Role::getName).collect(Collectors.toSet());
        if ((roles.contains("PASSENGER")) || roles.contains("ADMIN")) {
            return reservedSeatService.displayAllAvailableSeats(flightId);
        } else throw new UserNotFoundException();
    }

    @SeatChangePermission
    @PutMapping({"/{flightId}/tickets/{ticketId}/seats"})
    @ResponseStatus(HttpStatus.OK)
    public ReservedSeatDto changeSeat(@AuthenticationPrincipal User user, @PathVariable UUID flightId, @PathVariable UUID ticketId,
                                      @NotBlank String newSeat) throws IllegalAccessException {
        roles = user.getRoles().stream().map(Role::getName).collect(Collectors.toSet());

        if (roles.contains("ADMIN")) {
            return reservedSeatService.changeSeat(ticketId, flightId, newSeat);
        }
        if (user.getPassenger() != null) {
            passenger = passengerService.findPassengerEntityByIdOptional(user.getPassenger().getId());
            TicketEntity ticketEntity = ticketService.findById(ticketId)
                    .orElseThrow(TicketNotFoundException::new);

            if (passenger.isPresent() && passenger.get().getId() == ticketEntity.getPassengerEntity().getId()) {
                return reservedSeatService.changeSeat(ticketId, flightId, newSeat);
            } else throw new IllegalAccessException("Illegal Access");
        } else throw new UserNotFoundException("User Not Found");
    }
}
