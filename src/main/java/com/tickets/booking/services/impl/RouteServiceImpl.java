package com.tickets.booking.services.impl;

import com.tickets.booking.domain.FlightEntity;
import com.tickets.booking.domain.RouteEntity;
import com.tickets.booking.repository.FlightRepository;
import com.tickets.booking.repository.RouteRepository;
import com.tickets.booking.services.RouteService;
import com.tickets.booking.services.exceptions.RouteNotFoundException;
import com.tickets.booking.web.mappers.FlightMapper;
import com.tickets.booking.web.mappers.RouteMapper;
import com.tickets.booking.web.model.FlightDto;
import com.tickets.booking.web.model.RouteDto;
import com.tickets.booking.web.model.RoutePagedList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RouteServiceImpl implements RouteService {

    private final RouteRepository routeRepository;
    private final RouteMapper routeMapper;
    private final FlightMapper flightMapper;

    public RouteServiceImpl(RouteRepository routeRepository, RouteMapper routeMapper, FlightMapper flightMapper) {
        this.routeRepository = routeRepository;
        this.routeMapper = routeMapper;
        this.flightMapper = flightMapper;
    }

    @Override
    public RoutePagedList findAllRoutes(PageRequest pageRequest) {
        RoutePagedList routePagedList;
        Page<RouteEntity> routePage;

        routePage = routeRepository.findAll(pageRequest);

        routePagedList = new RoutePagedList(routePage
                .getContent()
                .stream()
                .map(routeMapper::routeToRouteDto)
                .collect(Collectors.toList()),
                PageRequest
                        .of(routePage.getPageable().getPageNumber(),
                                routePage.getPageable().getPageSize()),
                routePage.getTotalElements());

        return routePagedList;
    }

    @Override
    public Optional<RouteEntity> findById(UUID routeId) {
        return routeRepository.findById(routeId);
    }

    @Override
    public RouteDto updateRoute(RouteDto routeDto) {
        RouteEntity routeEntity = routeRepository.findById(routeDto.getId())
                .orElseThrow(() -> new RouteNotFoundException("Route Not Found Exception"));
        routeMapper.updateRouteFromDto(routeDto, routeEntity);
        routeRepository.save(routeEntity);
        return routeMapper.routeToRouteDto(routeEntity);
    }

    @Override
    public RouteDto createNewRoute(RouteDto routeDto) {
        return saveAndReturnDto(routeMapper.routeDtoToRoute(routeDto));
    }

    @Override
    public void saveRoute(RouteEntity routeEntity) {
        if (routeEntity != null) {
            routeRepository.save(routeEntity);
        } else {
            throw new RouteNotFoundException("Route Not Found Exception");
        }
    }

    @Override
    public UUID getRouteIdByOriginAndDestination(String origin, String destination) {
        return routeRepository.findRouteEntityByOriginAndDestination(origin, destination)
                .orElseThrow(RouteNotFoundException::new).getId();
    }

    private RouteDto saveAndReturnDto(RouteEntity routeEntity) {
        RouteEntity savedRoute = routeRepository.save(routeEntity);
        return routeMapper.routeToRouteDto(savedRoute);
    }

//    @Override
//    public List<FlightDto> showFlightsOnRoute(UUID routeId) {
//        return routeRepository.findFlightEntitiesByRouteId(routeId)
//                .stream()
//                .map(flightMapper::flightToFlightDto)
//                .collect(Collectors.toList());
//    }
}
