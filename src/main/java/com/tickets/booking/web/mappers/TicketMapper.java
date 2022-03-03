package com.tickets.booking.web.mappers;

import com.tickets.booking.domain.TicketEntity;
import com.tickets.booking.web.model.TicketDto;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TicketMapper {

    TicketMapper TICKET_MAPPER_INSTANCE = Mappers.getMapper(TicketMapper.class);

    TicketEntity ticketDtoToTicket(TicketDto ticketDto);

    TicketDto ticketToTicketDto(TicketEntity ticketEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateTicketFromDto(TicketDto ticketDto, @MappingTarget TicketEntity ticketEntity);
}
