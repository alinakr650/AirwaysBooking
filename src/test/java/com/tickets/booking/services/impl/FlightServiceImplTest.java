package com.tickets.booking.services.impl;

import com.tickets.booking.BaseTestClass;
import com.tickets.booking.domain.FlightEntity;
import com.tickets.booking.repository.FlightRepositoryCustom;
import com.tickets.booking.services.FlightService;
import com.tickets.booking.web.mappers.FlightMapper;
import com.tickets.booking.web.model.FlightDto;
import com.tickets.booking.web.model.PassengerDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class FlightServiceImplTest extends BaseTestClass {
    @Autowired
    FlightMapper flightMapper;

    @Autowired
    FlightService flightService;

    @Autowired
    FlightRepositoryCustom flightRepositoryCustom;

    @BeforeEach
    void setUp() {
        saveEntities();
    }

    @Test
    void findById() {
        FlightEntity flightEntity = flightRepository.findAll().get(0);
        FlightDto flightDto = flightMapper.flightToFlightDto(flightService.findById(flightEntity.getId()).get());

        assertThat(flightDto.getId()).isEqualTo(flightEntity.getId());
    }

    @Test
    void updateFlight() {
        FlightEntity flightEntity = flightRepository.findAll().get(0);
        UUID id = flightEntity.getId();
        flightEntity.setPrice(BigDecimal.valueOf(100.07));

        flightService.updateFlight(flightMapper.flightToFlightDto(flightEntity));

        FlightEntity updatedEntity = flightRepository.findById(id).get();
        assertThat(updatedEntity.getPrice()).isEqualTo(BigDecimal.valueOf(100.07));
    }

    @Test
    void createNewFlightEntity() {
        long initialSize = flightRepository.count();
        FlightDto savedFlightDto = flightService.createNewFlight(returnValidDto());

        long updatedSize = flightRepository.count();

        assertThat(savedFlightDto.getId()).isNotNull();
        assertThat(updatedSize).isEqualTo(initialSize + 1);
    }

    @Test
    void findFlightEntitiesById() {
        UUID id = flightRepository.findAll().get(0).getId();
        UUID id1 = flightRepository.findAll().get(1).getId();

        List<FlightDto> listOfFlights = flightService.findFlightsById(List.of(id, id1));

        assertThat(listOfFlights.size()).isEqualTo(2);
    }

    @Test
    void findIndirectPath() {
        Map<FlightEntity, FlightEntity> jointRoute = flightService.findIndirectPath("London", "Cardiff", LocalDate.of(2022, 3, 21));

        assertThat(jointRoute.values()).isNotNull();
        assertThat(jointRoute.keySet()).isNotNull(); //Returns an empty Map since no indirect flights exist in a database yet

    }

    @Test
    void searchFlightEntities() {
        List<UUID> flights = flightRepositoryCustom.searchFlightEntities("London", null, LocalDate.of(2022, 3, 21), null, null, "Tr", null);

        assertThat(flights.size()).isEqualTo(1);
    }

    @Test
    void findAllPassengersOnFlight() {
        List<PassengerDto> passengers = flightService.findAllPassengersOnFlight(flightRepository.findAll().get(0));

        assertThat(passengers.size()).isEqualTo(1);
    }

    @Test
    void deleteFlightById() {
        FlightEntity flightEntity = flightRepository.findAll().get(0);
        flightService.deleteFlightById(flightEntity.getId());

        assertThat(flightRepository.findById(flightEntity.getId())).isEmpty();
    }

    private FlightDto returnValidDto() {
        FlightDto flightDto = new FlightDto();
        flightDto.setPlaneName("BOEING_717");
        flightDto.setFlightNumber(routeRepository.findAll().get(0).getFlightNumber());
        flightDto.setRouteId(routeRepository.findAll().get(0).getId());
        flightDto.setTimeOfDeparture(LocalTime.of(8, 40, 37));
        flightDto.setTimeOfArrival(LocalTime.of(10, 25, 23));
        flightDto.setDateOfDeparture(LocalDate.of(2022, 04, 12));
        flightDto.setDateOfArrival(LocalDate.of(2022, 04, 12));
        flightDto.setPrice(BigDecimal.valueOf(56.45));
        return flightDto;
    }
}