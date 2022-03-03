package com.tickets.booking.services;

import com.tickets.booking.domain.TicketEntity;
import com.tickets.booking.web.model.TicketDto;
import com.tickets.booking.web.model.TicketRequisite;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Transactional
public interface TicketService {

    Optional<TicketEntity> findById(UUID ticketId);

    void deleteTicketById(UUID id);

    void updateTicket(TicketRequisite ticketRequisite, UUID id);

    TicketRequisite createNewTicketWithRequisites(TicketRequisite ticketRequisite);

    int countAllTicketsById(UUID id);

}
