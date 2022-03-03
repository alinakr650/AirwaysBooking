package com.tickets.booking.repository;

import com.tickets.booking.domain.TicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Validated
@Repository
public interface TicketsRepository extends JpaRepository<TicketEntity, UUID> {

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = "DELETE FROM ticket WHERE ticket_id = ?1", nativeQuery = true)
    void deleteById(UUID ticketId);

    @Query(value = "SELECT COUNT(*) FROM reserved_seats WHERE ticket_id =:id", nativeQuery = true)
    int countAllTicketsById(UUID id);
}
