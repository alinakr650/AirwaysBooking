package com.tickets.booking.web.mappers;

import com.tickets.booking.domain.ReservedSeatEntity;
import com.tickets.booking.web.model.ReservedSeatDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ReservedSeatMapper {

    ReservedSeatMapper RESERVEDSEAT_MAPPER_INSTANCE = Mappers.getMapper(ReservedSeatMapper.class);

    ReservedSeatEntity reservedSeatDtoToReservedSeat(ReservedSeatDto reservedSeatDto);

    ReservedSeatDto reservedSeatToReservedSeatDto(ReservedSeatEntity reservedSeatEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateReservedSeatFromDto(ReservedSeatDto reservedSeatDto, @MappingTarget ReservedSeatEntity reservedSeatEntity);
}


