package com.tickets.booking.web.controller;

import com.tickets.booking.security.permissions.PassengerCreatePermission;
import com.tickets.booking.security.permissions.PassengerDeletePermission;
import com.tickets.booking.security.permissions.PassengerReadPermission;
import com.tickets.booking.security.permissions.PassengerUpdatePermission;
import com.tickets.booking.services.PassengerService;
import com.tickets.booking.web.model.PassengerDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/passengers")
public class PassengerController {

    private final PassengerService passengerService;

    public PassengerController(PassengerService passengerService) {
        this.passengerService = passengerService;
    }

    @PassengerReadPermission
    @GetMapping({"/{passengerId}"})
    @ResponseStatus(HttpStatus.OK)
    public PassengerDto findPassengerById(@PathVariable UUID passengerId) {
        return passengerService.findPassengerById(passengerId);
    }

    @PassengerCreatePermission
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PassengerDto createNewPassenger(@RequestBody PassengerDto passengerDto) {
        return passengerService.createNewPassenger(passengerDto);
    }

    @PassengerUpdatePermission
    @PutMapping({"/{passengerId}"})
    public ResponseEntity<Void> updatePassenger(@RequestBody PassengerDto passengerDto, @PathVariable UUID passengerId) {
        passengerDto.setId(passengerId);
        passengerService.updatePassenger(passengerDto);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PassengerDeletePermission
    @DeleteMapping({"/{passengerId}"})
    public ResponseEntity deletePassenger(@PathVariable UUID passengerId) {
        passengerService.deletePassengerById(passengerId);
        return new ResponseEntity(HttpStatus.OK);
    }
}
