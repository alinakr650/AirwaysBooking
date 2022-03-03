package com.tickets.booking.web.mappers;


import com.tickets.booking.domain.PassengerEntity;
import com.tickets.booking.web.model.PassengerDto;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PassengerMapper {

    PassengerMapper PASSENGER_MAPPER_INSTANCE = Mappers.getMapper(PassengerMapper.class);

    PassengerEntity passengerDtoToPassenger(PassengerDto passengerDto);

    PassengerDto passengerToPassengerDto(PassengerEntity passengerEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updatePassengerFromDto(PassengerDto ticketDto, @MappingTarget PassengerEntity passengerEntity);
}
