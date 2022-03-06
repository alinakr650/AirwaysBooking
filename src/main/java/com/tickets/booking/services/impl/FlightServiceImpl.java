package com.tickets.booking.services.impl;

import com.tickets.booking.domain.FlightEntity;
import com.tickets.booking.domain.RouteEntity;
import com.tickets.booking.domain.TicketEntity;
import com.tickets.booking.repository.FlightRepository;
import com.tickets.booking.repository.FlightRepositoryCustom;
import com.tickets.booking.repository.RouteRepository;
import com.tickets.booking.services.FlightService;
import com.tickets.booking.services.exceptions.FlightNotFoundException;
import com.tickets.booking.web.mappers.FlightMapper;
import com.tickets.booking.web.mappers.PassengerMapper;
import com.tickets.booking.web.model.FlightDto;
import com.tickets.booking.web.model.PassengerDto;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FlightServiceImpl implements FlightService {

    private final FlightMapper flightMapper;
    private final FlightRepository flightRepository;
    private final RouteRepository routeRepository;
    private final FlightRepositoryCustom flightRepositoryCustom;
    private final PassengerMapper passengerMapper;

    public FlightServiceImpl(FlightMapper flightMapper, FlightRepository flightRepository, RouteRepository routeRepository, FlightRepositoryCustom flightRepositoryCustom, PassengerMapper passengerMapper) {
        this.flightMapper = flightMapper;
        this.flightRepository = flightRepository;
        this.routeRepository = routeRepository;
        this.flightRepositoryCustom = flightRepositoryCustom;
        this.passengerMapper = passengerMapper;
    }

    @Override
    public Map<FlightEntity, FlightEntity> findIndirectPath(String origin, String destination, LocalDate dateOfDeparture) {

        List<FlightEntity> originFlights = flightRepository.findFlightsByDepartureDateAndOrigin(origin, dateOfDeparture);

        List<FlightEntity> destinationFlights = new ArrayList<>();
        originFlights.forEach(fl ->
                destinationFlights.addAll
                        (flightRepository.findFlightsByDepartureDateAndOriginAndDestination(fl.getDateOfArrival(),
                                fl.getRouteEntity().getDestination(), destination, fl.getTimeOfArrival().plusMinutes(20))));

        Map<FlightEntity, FlightEntity> jointRoute = new LinkedHashMap<>();

        for (FlightEntity originFlight : originFlights) {
            for (FlightEntity destinationFlight : destinationFlights) {
                if (originFlight.getRouteEntity().getDestination().equalsIgnoreCase(destinationFlight
                        .getRouteEntity().getOrigin()) && destinationFlight.getTimeOfDeparture()
                        .isAfter(originFlight.getTimeOfArrival())) {
                    jointRoute.put(originFlight, destinationFlight);
                }
            }
        }
        return jointRoute;
    }

    @Override
    public Optional<FlightEntity> findById(UUID flightId) {
        return flightRepository.findById(flightId);
    }

    @Override
    public FlightDto updateFlight(FlightDto flightDto) {
        FlightEntity flightEntity = flightRepository.findById(flightDto.getId())
                .orElseThrow(() -> new FlightNotFoundException("Flight Not Found Exception"));
        flightMapper.updateFlightFromDto(flightDto, flightEntity);
        flightRepository.save(flightEntity);
        return flightMapper.flightToFlightDto(flightEntity);
    }


    @Override
    public List<PassengerDto> findAllPassengersOnFlight(FlightEntity flightEntity) {
        List<TicketEntity> ticketsOnFlight = flightEntity.getTicketEntities();
        return ticketsOnFlight
                .stream()
                .map(TicketEntity::getPassengerEntity)
                .collect(Collectors.toList())
                .stream()
                .map(passengerMapper::passengerToPassengerDto)
                .collect(Collectors.toList());
    }

    @Override
    public FlightDto createNewFlight(FlightDto flightDto) {
        FlightEntity flightEntity = flightMapper.flightDtoToFlight(flightDto);
        RouteEntity routeEntity = routeRepository.findRouteEntityByFlightNumber(flightDto.getFlightNumber());
        routeEntity.addFlight(flightEntity);
        RouteEntity savedRouteEntity = routeRepository.saveAndFlush(routeEntity);
        FlightEntity savedFlightEntity = flightRepository.findFlightEntityByRouteIdAndDepartureDateAndDepartureTime(savedRouteEntity.getId(),
                flightDto.getDateOfDeparture(), flightDto.getTimeOfDeparture()).orElseThrow(FlightNotFoundException::new);
        return flightMapper.flightToFlightDto(savedFlightEntity);
    }


    @Override
    public List<FlightDto> findFlightsById(List<UUID> ids) {
        return flightRepository.findFlightEntitiesById(ids)
                .stream()
                .map(flightMapper::flightToFlightDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<UUID> searchFlights(String origin, String destination, LocalDate dateOfDeparture, BigDecimal price,
                                    String orderByPriceAsc, String orderByPriceDsc, String orderByFlightDurationAsc) {
        return flightRepositoryCustom.searchFlightEntities(origin, destination, dateOfDeparture, price,
                orderByPriceAsc, orderByPriceDsc, orderByFlightDurationAsc);
    }

    @Override
    public void deleteFlightById(UUID id) {
        if (id != null) {
            flightRepository.deleteById(id)
            ;
        } else {
            throw new FlightNotFoundException("Flight Not Found Exception");
        }
    }

}
