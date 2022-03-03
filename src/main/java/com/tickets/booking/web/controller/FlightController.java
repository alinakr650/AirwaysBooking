package com.tickets.booking.web.controller;

import com.tickets.booking.domain.FlightEntity;
import com.tickets.booking.security.permissions.*;
import com.tickets.booking.services.FlightService;
import com.tickets.booking.services.exceptions.FlightNotFoundException;
import com.tickets.booking.web.mappers.FlightMapper;
import com.tickets.booking.web.model.FlightDto;
import com.tickets.booking.web.model.PassengerDto;
import com.tickets.booking.web.model.RouteDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/flights")
public class FlightController {

    private final FlightService flightService;
    private final FlightMapper flightMapper;

    public FlightController(FlightService flightService, FlightMapper flightMapper) {
        this.flightService = flightService;
        this.flightMapper = flightMapper;
    }

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("flightId");
    }

    @CrossOrigin
    @FlightReadPermission
    @GetMapping({"/{flightId}"})
    @ResponseStatus(HttpStatus.OK)
    public FlightDto findFlightById(@PathVariable UUID flightId) {
        FlightEntity flightEntity = flightService.findById(flightId).orElseThrow(FlightNotFoundException::new);
        FlightDto flightDto = flightMapper.flightToFlightDto(flightEntity);
        flightDto.setRouteId(flightEntity.getRouteEntity().getId());
        return flightDto;
    }


    @CrossOrigin
    @FlightReadPermission
    @GetMapping({"/search"})
    @ResponseStatus(HttpStatus.OK)
    public List<FlightDto> searchThroughFlights(@RequestParam(name = "origin") @NotBlank String origin,
                                                   @RequestParam(name = "destination", required = false) @Nullable String destination,
                                                   @RequestParam(name = "dateOfDeparture") @NotNull String dateOfDeparture,
                                                   @RequestParam(name = "priceLimit", required = false) @Nullable BigDecimal priceLimit,
                                                   @Nullable @RequestParam(name = "orderByPriceAsc", required = false) String orderByPriceAsc,
                                                   @Nullable @RequestParam(name = "orderByPriceDsc", required = false) String orderByPriceDsc,
                                                   @RequestParam(name = "orderByFlightDurationAsc", required = false) @Nullable String orderByFlightDurationAsc) {
        LocalDate date = LocalDate.parse(dateOfDeparture);
        List<UUID> flightIds = flightService.searchFlights(origin, destination, date, priceLimit, orderByPriceAsc,
                orderByPriceDsc, orderByFlightDurationAsc);
        return flightService.findFlightsById(flightIds);
    }

    @FlightReadPermission
    @GetMapping("/indirect-path")
    @ResponseStatus(HttpStatus.CREATED)
    public Map<FlightEntity, FlightEntity> findIndirectFlight(@RequestParam(name = "origin") @NotBlank String origin,
                                                              @RequestParam(name = "destination") @NotBlank String destination,
                                                              @RequestParam(name = "dateOfDeparture") @NotNull LocalDate dateOfDeparture) {
        return flightService.findIndirectPath(origin, destination, dateOfDeparture);
    }

    @PreAuthorize("hasRole(ADMIN)")
    @GetMapping("/{flightId}/passengers_on_flight")
    @ResponseStatus(HttpStatus.OK)
    public List<PassengerDto> showAllPassengersOnFlight(@PathVariable UUID flightId) {
        return flightService.findAllPassengersOnFlight(flightService.findById(flightId)
                .orElseThrow(FlightNotFoundException::new));
    }

    @FlightCreatePermission
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FlightDto createNewFlight(@RequestBody FlightDto flightDto) {
        return flightService.createNewFlight(flightDto);
    }

    @FlightUpdatePermission
    @PutMapping({"/{flightId}"})
    @ResponseStatus(HttpStatus.OK)
    public FlightDto updateFlight(@RequestBody FlightDto flightDto, @PathVariable UUID flightId) {
        flightDto.setId(flightId);
        return flightService.updateFlight(flightDto);
    }

    @FlightDeletePermission
    @DeleteMapping({"/{id}"})
    public ResponseEntity deleteFlight(@PathVariable UUID id) {
        flightService.deleteFlightById(id);
        return new ResponseEntity(HttpStatus.OK);
    }



}
