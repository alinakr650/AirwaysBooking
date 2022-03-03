package com.tickets.booking.web.mappers;

import com.tickets.booking.domain.FlightEntity;
import com.tickets.booking.web.model.FlightDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper
public interface FlightMapper {

    FlightMapper FLIGHT_MAPPER_INSTANCE = Mappers.getMapper(FlightMapper.class);

    FlightEntity flightDtoToFlight(FlightDto flightDto);

    FlightDto flightToFlightDto(FlightEntity flightEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFlightFromDto(FlightDto flightDto, @MappingTarget FlightEntity flightEntity);
}
