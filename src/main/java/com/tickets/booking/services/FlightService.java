package com.tickets.booking.services;

import com.tickets.booking.domain.FlightEntity;
import com.tickets.booking.domain.PassengerEntity;
import com.tickets.booking.domain.RouteEntity;
import com.tickets.booking.web.model.FlightDto;
import com.tickets.booking.web.model.PassengerDto;
import com.tickets.booking.web.model.RouteDto;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Transactional
public interface FlightService {

    void deleteFlightById(UUID id);

    Map<FlightEntity, FlightEntity> findIndirectPath(String origin, String destination, LocalDate dateOfDeparture);

    Optional<FlightEntity> findById(UUID flightId);

    FlightDto updateFlight(FlightDto flightDto);

    FlightDto createNewFlight(FlightDto flightDto);

    List<FlightDto> findFlightsById(List<UUID> ids);

    List<PassengerDto> findAllPassengersOnFlight(FlightEntity flightEntity);

    List<UUID> searchFlights(String origin, String destination, LocalDate dateOfDeparture, BigDecimal price, String orderByPriceAsc, String orderByPriceDsc, String orderByFlightDurationAsc);
}
