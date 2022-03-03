package com.tickets.booking;

import com.tickets.booking.domain.*;
import com.tickets.booking.domain.nationalities.Nationality;
import com.tickets.booking.repository.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@SpringBootTest
@ActiveProfiles("test")
public class BaseTestClass {

    @Autowired
    public RouteRepository routeRepository;

    @Autowired
    public TicketsRepository ticketsRepository;

    @Autowired
    public FlightRepository flightRepository;

    @Autowired
    public PassengerRepository passengerRepository;

    @Autowired
    public ReservedSeatRepository reservedSeatRepository;

    @AfterEach
    void deleteAll(){
        reservedSeatRepository.deleteAll();
        passengerRepository.deleteAll();
        ticketsRepository.deleteAll();
        flightRepository.deleteAll();
        routeRepository.deleteAll();
    }

    public void saveEntities(){
        RouteEntity routeEntity = new RouteEntity();
        routeEntity.setOrigin("London");
        routeEntity.setDestination("Cardiff");
        routeEntity.setFlightNumber("CAR30290492");

        RouteEntity routeEntity1 = new RouteEntity();
        routeEntity1.setOrigin("Cardiff");
        routeEntity1.setDestination("London");
        routeEntity1.setFlightNumber("LO48593452");

        routeRepository.saveAndFlush(routeEntity1);
        routeRepository.saveAndFlush(routeEntity);

        FlightEntity flightEntity = new FlightEntity();
        flightEntity.setRouteEntity(routeEntity);
        flightEntity.setPlaneName("BOEING_717");
        flightEntity.setFlightNumber(routeEntity.getFlightNumber());
        flightEntity.setTimeOfArrival(LocalTime.of(5,40,0));
        flightEntity.setTimeOfDeparture(LocalTime.of(3,10, 0));
        flightEntity.setDateOfArrival(LocalDate.of(2022, 3, 21));
        flightEntity.setDateOfDeparture(LocalDate.of(2022, 3, 21));
        flightEntity.setPrice(new BigDecimal("112.50"));

        FlightEntity flightEntity1 = new FlightEntity();
        flightEntity1.setRouteEntity(routeEntity1);
        flightEntity1.setPlaneName("AIRBUS_A320");
        flightEntity1.setFlightNumber(routeEntity1.getFlightNumber());
        flightEntity1.setTimeOfArrival(LocalTime.of(12,15,0));
        flightEntity1.setTimeOfDeparture(LocalTime.of(10,5, 0));
        flightEntity1.setDateOfArrival(LocalDate.of(2022, 4, 11));
        flightEntity1.setDateOfDeparture(LocalDate.of(2022, 4, 11));
        flightEntity1.setPrice(new BigDecimal("97.45"));

        PassengerEntity passengerEntity1 = new PassengerEntity();
        passengerEntity1.setFirstName("John");
        passengerEntity1.setMiddleName("Hamish");
        passengerEntity1.setLastName("Watson");
        passengerEntity1.setPassportNumber("JK8438084");
        passengerEntity1.setNationality(Nationality.UK);

        passengerRepository.saveAndFlush(passengerEntity1);

        TicketEntity ticketEntity1 = new TicketEntity();
        ticketEntity1.setActive(false);
        ticketEntity1.setFlightEntity(flightEntity);
        ticketEntity1.setPassengerEntity(passengerEntity1);

        TicketEntity ticketEntity2 = new TicketEntity();
        ticketEntity2.setActive(true);
        ticketEntity2.setFlightEntity(flightEntity1);
        ticketEntity2.setPassengerEntity(passengerEntity1);

        flightEntity.setTicketEntities(List.of(ticketEntity1));
        flightEntity1.setTicketEntities(List.of(ticketEntity2));

        ReservedSeatEntity reservedSeatEntity = new ReservedSeatEntity();
        reservedSeatEntity.setFlightEntity(flightEntity);
        reservedSeatEntity.setTicketEntity(ticketEntity1);
        reservedSeatEntity.setSeatNumber("A9");

        ReservedSeatEntity reservedSeatEntity1 = new ReservedSeatEntity();
        reservedSeatEntity1.setFlightEntity(flightEntity1);
        reservedSeatEntity1.setTicketEntity(ticketEntity2);
        reservedSeatEntity1.setSeatNumber("B12");

        reservedSeatRepository.save(reservedSeatEntity);
        reservedSeatRepository.save(reservedSeatEntity1);
    }
}
