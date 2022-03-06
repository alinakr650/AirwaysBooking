package com.tickets.booking.services;

import com.tickets.booking.domain.RouteEntity;
import com.tickets.booking.web.model.RouteDto;
import com.tickets.booking.web.model.RoutePagedList;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
import java.util.UUID;

@Transactional
public interface RouteService {

    RoutePagedList findAllRoutes(PageRequest pageRequest);

    Optional<RouteEntity> findById(UUID routeId);

    RouteDto updateRoute(RouteDto routeDto);

    RouteDto createNewRoute(RouteDto routeDto);

    void saveRoute(RouteEntity routeEntity);

    UUID getRouteIdByOriginAndDestination(String origin, String destination);
}
