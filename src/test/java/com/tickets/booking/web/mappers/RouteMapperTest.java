package com.tickets.booking.web.mappers;

import com.tickets.booking.domain.FlightEntity;
import com.tickets.booking.domain.RouteEntity;
import com.tickets.booking.web.model.RouteDto;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class RouteMapperTest {

    RouteMapper routeMapper = RouteMapper.ROUTE_MAPPER_INSTANCE;

    UUID id = UUID.randomUUID();

    @Test
    void routeDtoToRoute() {

        RouteDto routeDto = new RouteDto();
        routeDto.setId(id);
        routeDto.setOrigin("London");
        routeDto.setDestination("Cardiff");
        routeDto.setFlightNumber("CAR30290492");

        RouteEntity routeEntity = routeMapper.routeDtoToRoute(routeDto);

        assertEquals(id, routeDto.getId());
        assertEquals(routeEntity.getFlightNumber(), routeDto.getFlightNumber());
        assertEquals(routeEntity.getOrigin(), routeDto.getOrigin());
        assertEquals(routeEntity.getDestination(), routeDto.getDestination());
    }

    @Test
    void routeToRouteDto() {

        RouteEntity routeEntity = new RouteEntity();
        routeEntity.setId(id);
        routeEntity.setFlights(List.of(new FlightEntity()));
        routeEntity.setOrigin("London");
        routeEntity.setDestination("Cardiff");
        routeEntity.setFlightNumber("CAR30290492");

        RouteDto routeDto = routeMapper.routeToRouteDto(routeEntity);

        assertEquals(id, routeDto.getId());
        assertEquals(routeEntity.getFlightNumber(), routeDto.getFlightNumber());
        assertEquals(routeEntity.getOrigin(), routeDto.getOrigin());
        assertEquals(routeEntity.getDestination(), routeDto.getDestination());

    }

    @Test
    void updateRouteFromDto() {

        RouteDto routeDto = new RouteDto();
        routeDto.setId(id);;
        routeDto.setOrigin("London");
        routeDto.setDestination("Cardiff");
        routeDto.setFlightNumber("CAR30290492");

        RouteEntity routeEntity = new RouteEntity();

        routeMapper.updateRouteFromDto(routeDto, routeEntity);

        assertEquals(id, routeDto.getId());
        assertEquals(routeEntity.getFlightNumber(), routeDto.getFlightNumber());
        assertEquals(routeEntity.getOrigin(), routeDto.getOrigin());
        assertEquals(routeEntity.getDestination(), routeDto.getDestination());
    }
}