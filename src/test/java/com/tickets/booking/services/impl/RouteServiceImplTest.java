package com.tickets.booking.services.impl;

import com.tickets.booking.BaseTestClass;
import com.tickets.booking.domain.RouteEntity;
import com.tickets.booking.services.RouteService;
import com.tickets.booking.web.mappers.RouteMapper;
import com.tickets.booking.web.model.RouteDto;
import com.tickets.booking.web.model.RoutePagedList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class RouteServiceImplTest extends BaseTestClass {
    
    @Autowired
    RouteService routeService;

    @Autowired
    RouteMapper routeMapper;

    @BeforeEach
    void setUp() {
        super.saveEntities();
    }

    @Test
    void findAllRoutes() {
        RoutePagedList routePagedList = routeService.findAllRoutes(PageRequest.of(0,10));
        assertThat(routePagedList.stream()
                .collect(Collectors.toList())
                .size()).isEqualTo(2);

        assertThat(routePagedList.stream()
                .map(RouteDto::getDestination)
                .collect(Collectors.toList())
                .contains("Cardiff")).isTrue();
    }

    @Test
    void findById() {
        RouteEntity routeEntity = routeRepository.findAll().get(0);
        RouteDto routeDto = routeMapper.routeToRouteDto(routeService.findById(routeEntity.getId()).get());

        assertThat(routeDto.getId()).isEqualTo(routeEntity.getId());
    }

    @Test
    void updateRoute() {
        RouteEntity routeEntity = routeRepository.findAll().get(0);
        UUID id = routeEntity.getId();
        routeEntity.setOrigin("Florence");
        routeEntity.setDestination("Milan");
        routeEntity.setFlightNumber("MIL93094309");

        routeService.updateRoute(routeMapper.routeToRouteDto(routeEntity));

        RouteEntity updatedEntity = routeRepository.findById(id).get();
        assertThat(updatedEntity.getFlightNumber()).isEqualTo("MIL93094309");
    }

    @Test
    void createNewRouteEntity() {
        RouteDto savedRouteDto = routeService.createNewRoute(returnValidDto());

        assertThat(savedRouteDto.getId()).isNotNull();
    }

    @Test
    void saveRouteByDto() {
        routeService.saveRoute(routeMapper.routeDtoToRoute(returnValidDto()));

        assertThat(routeRepository.findAll().size()).isEqualTo(3);
    }


    @Test
    void getRouteIdByOriginAndDestination() {
        UUID id = routeService.getRouteIdByOriginAndDestination("London", "Cardiff");
        
        assertThat(id).isNotNull();
    }

    private RouteDto returnValidDto() {
        RouteDto routeDto = new RouteDto();
        routeDto.setOrigin("Florence");
        routeDto.setDestination("Milan");
        routeDto.setFlightNumber("MIL93094309");
        return routeDto;
    }
}