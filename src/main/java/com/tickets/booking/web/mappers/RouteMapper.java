package com.tickets.booking.web.mappers;


import com.tickets.booking.domain.RouteEntity;
import com.tickets.booking.web.model.RouteDto;
import liquibase.pro.packaged.M;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RouteMapper {
    RouteMapper ROUTE_MAPPER_INSTANCE = Mappers.getMapper(RouteMapper.class);

    RouteEntity routeDtoToRoute(RouteDto routeDto);

    RouteDto routeToRouteDto(RouteEntity routeEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateRouteFromDto(RouteDto routeDto, @MappingTarget RouteEntity routeEntity);
}
