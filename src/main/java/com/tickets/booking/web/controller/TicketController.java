package com.tickets.booking.web.controller;

import com.tickets.booking.security.permissions.*;
import com.tickets.booking.services.PassengerService;
import com.tickets.booking.services.TicketService;
import com.tickets.booking.services.exceptions.TicketNotFoundException;
import com.tickets.booking.web.mappers.TicketMapper;
import com.tickets.booking.web.model.TicketDto;
import com.tickets.booking.web.model.TicketRequisite;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    private final TicketService ticketService;
    private final TicketMapper ticketMapper;
    private final PassengerService passengerService;


    public TicketController(TicketService ticketService, TicketMapper ticketMapper, PassengerService passengerService) {
        this.ticketService = ticketService;
        this.ticketMapper = ticketMapper;
        this.passengerService = passengerService;
    }

    @TicketReadPermission
    @GetMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public TicketDto findTicketById(@PathVariable UUID id) {
        return ticketMapper.ticketToTicketDto(ticketService.findById(id)
                .orElseThrow(() -> new TicketNotFoundException("Ticket Not Found")));
    }

    @TicketReadPermission
    @GetMapping({"/{passengerId}/tickets"})
    @ResponseStatus(HttpStatus.OK)
    public List<TicketDto> findTicketsByPassenger(@PathVariable UUID passengerId) {
        return passengerService.findTicketsByPassenger(passengerId);
    }

    @TicketCreatePermission
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TicketRequisite createNewTicket(@RequestBody TicketRequisite ticketRequisite) {
        return ticketService.createNewTicketWithRequisites(ticketRequisite);
    }

    @TicketUpdatePermission
    @PutMapping({"/{id}"})
    public ResponseEntity<Void> updateTicket(@RequestBody TicketRequisite ticketRequisite, @PathVariable UUID id) {
        ticketService.updateTicket(ticketRequisite, id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @TicketDeletePermission
    @DeleteMapping({"/{id}"})
    public ResponseEntity deleteTicket(@PathVariable UUID id) {
        ticketService.deleteTicketById(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
