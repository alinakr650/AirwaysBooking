package com.tickets.booking.web.mappers;

import com.tickets.booking.BaseTestClass;
import com.tickets.booking.domain.FlightEntity;
import com.tickets.booking.domain.RouteEntity;
import com.tickets.booking.repository.FlightRepository;
import com.tickets.booking.web.model.FlightDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class FlightMapperTest extends BaseTestClass {

    @Autowired
    FlightRepository flightRepository;

    FlightEntity flightEntity;

    FlightMapper flightMapper = FlightMapper.FLIGHT_MAPPER_INSTANCE;

    UUID id = UUID.randomUUID();

    UUID routeId = UUID.randomUUID();

    @BeforeEach
    public void setUp() {
        saveEntities();
        flightEntity = flightRepository.findAll().get(0);
    }

    @Test
    void flightDtoToFlight() {

        FlightDto flightDto = new FlightDto();
        flightDto.setId(flightEntity.getId());
        flightDto.setRouteId(flightEntity.getRouteEntity().getId());
        flightDto.setPlaneName(flightEntity.getPlaneName());
        flightDto.setFlightNumber(flightEntity.getFlightNumber());
        flightDto.setTimeOfArrival(flightEntity.getTimeOfArrival());
        flightDto.setTimeOfDeparture(flightEntity.getTimeOfDeparture());
        flightDto.setDateOfArrival(flightEntity.getDateOfArrival());
        flightDto.setDateOfDeparture(flightEntity.getDateOfDeparture());

        FlightEntity flight = flightMapper.flightDtoToFlight(flightDto);

        assertEquals(flight.getId(), flightDto.getId());
        assertEquals(flight.getPlaneName(), flightDto.getPlaneName());
        assertEquals(flight.getFlightNumber(), flightDto.getFlightNumber());
        assertEquals(flight.getTimeOfArrival(), flightDto.getTimeOfArrival());
        assertEquals(flight.getTimeOfDeparture(), flightDto.getTimeOfDeparture());
        assertEquals(flight.getDateOfDeparture(), flightDto.getDateOfDeparture());
        assertEquals(flight.getDateOfArrival(), flightDto.getDateOfArrival());

    }

    @Test
    void flightToFlightDto() {

        FlightEntity flightEntity = new FlightEntity();
        flightEntity.setId(id);
        flightEntity.setRouteEntity(new RouteEntity());
        flightEntity.setPlaneName("BOEING_717");
        flightEntity.setFlightNumber("CAR30290492");
        flightEntity.setTimeOfArrival(LocalTime.of(3, 40, 0));
        flightEntity.setTimeOfDeparture(LocalTime.of(5, 10, 0));
        flightEntity.setDateOfArrival(LocalDate.of(2022, 3, 21));
        flightEntity.setDateOfDeparture(LocalDate.of(2022, 3, 25));

        FlightDto flightDto = flightMapper.flightToFlightDto(flightEntity);

        assertEquals(id, flightDto.getId());
        assertEquals(flightEntity.getPlaneName(), flightDto.getPlaneName());
        assertEquals(flightEntity.getFlightNumber(), flightDto.getFlightNumber());
        assertEquals(flightEntity.getTimeOfArrival(), flightDto.getTimeOfArrival());
        assertEquals(flightEntity.getTimeOfDeparture(), flightDto.getTimeOfDeparture());
        assertEquals(flightEntity.getDateOfDeparture(), flightDto.getDateOfDeparture());
        assertEquals(flightEntity.getDateOfArrival(), flightDto.getDateOfArrival());

    }

    @Test
    void updateFlightFromDto() {

        FlightDto flightDto = new FlightDto();
        flightDto.setId(id);
        flightDto.setRouteId(routeId);
        flightDto.setPlaneName("BOEING_717");
        flightDto.setFlightNumber("CAR30290492");
        flightDto.setTimeOfArrival(LocalTime.of(3, 40, 0));
        flightDto.setTimeOfDeparture(LocalTime.of(5, 10, 0));
        flightDto.setDateOfArrival(LocalDate.of(2022, 3, 21));
        flightDto.setDateOfDeparture(LocalDate.of(2022, 3, 25));

        FlightEntity flightEntity = new FlightEntity();

        flightMapper.updateFlightFromDto(flightDto, flightEntity);

        assertEquals(id, flightDto.getId());
        assertEquals(flightEntity.getPlaneName(), flightDto.getPlaneName());
        assertEquals(flightEntity.getFlightNumber(), flightDto.getFlightNumber());
        assertEquals(flightEntity.getTimeOfArrival(), flightDto.getTimeOfArrival());
        assertEquals(flightEntity.getTimeOfDeparture(), flightDto.getTimeOfDeparture());
        assertEquals(flightEntity.getDateOfDeparture(), flightDto.getDateOfDeparture());
        assertEquals(flightEntity.getDateOfArrival(), flightDto.getDateOfArrival());

    }
}