package com.tickets.booking.web.controller;

import com.tickets.booking.security.permissions.PassengerUpdatePermission;
import com.tickets.booking.security.permissions.RouteCreatePermission;
import com.tickets.booking.security.permissions.RouteReadPermission;
import com.tickets.booking.services.RouteService;
import com.tickets.booking.services.exceptions.RouteNotFoundException;
import com.tickets.booking.web.mappers.RouteMapper;
import com.tickets.booking.web.model.RouteDto;
import com.tickets.booking.web.model.RoutePagedList;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/routes")
public class RouteController {

    private static final Integer DEFAULT_PAGE_SIZE = 30;
    private static final Integer DEFAULT_PAGE_NUMBER = 0;

    private final RouteService routeService;
    private final RouteMapper routeMapper;


    public RouteController(RouteService routeService, RouteMapper routeMapper) {
        this.routeService = routeService;
        this.routeMapper = routeMapper;
    }

    @RouteReadPermission
    @GetMapping
    public ResponseEntity<RoutePagedList> listRoutes(@RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                                     @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        if (pageNumber != null && pageNumber > 0) {
            pageNumber = DEFAULT_PAGE_NUMBER;
        }

        if (pageSize != null && pageSize > 0) {
            pageSize = DEFAULT_PAGE_SIZE;
        }

        RoutePagedList routePagedList = routeService.findAllRoutes(PageRequest.of(pageNumber, pageSize));

        return new ResponseEntity<>(routePagedList, HttpStatus.OK);
    }

    @RouteReadPermission
    @GetMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<RouteDto> findRouteById(@PathVariable UUID id) {
        RouteDto routeDto = routeMapper.routeToRouteDto(routeService.findById(id)
                .orElseThrow(() -> new RouteNotFoundException("Route Not Found")));
        EntityModel<RouteDto> entityModel = EntityModel.of(routeDto);
        WebMvcLinkBuilder linkToFlights = linkTo(methodOn(this.getClass()).listRoutes(DEFAULT_PAGE_NUMBER, DEFAULT_PAGE_SIZE));
        entityModel.add(linkToFlights.withRel("all-routes"));
        return entityModel;
    }

    @RouteCreatePermission
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RouteDto createNewRoute(@RequestBody RouteDto routeDto) {
        return routeService.createNewRoute(routeDto);
    }


    @PassengerUpdatePermission
    @PutMapping({"/{id}"})
    public ResponseEntity<Void> updateRouteInfo(@RequestBody RouteDto routeDto, @PathVariable UUID id) {
        routeDto.setId(id);
        routeService.updateRoute(routeDto);
        return new ResponseEntity(HttpStatus.OK);
    }
}
