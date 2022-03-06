package com.tickets.booking.repository;

import com.tickets.booking.domain.ReservedSeatEntity;
import com.tickets.booking.domain.TicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Validated
public interface ReservedSeatRepository extends JpaRepository<ReservedSeatEntity, UUID> {

    @Query(value = "SELECT * FROM reserved_seats r WHERE r.flight_id = :flightId",
            nativeQuery = true)
    List<ReservedSeatEntity> findAllByFlightEntityId(@NotNull UUID flightId);

    @Query(value = "SELECT re FROM ReservedSeatEntity re WHERE re.ticketEntity.id =:ticketId")
    Optional<ReservedSeatEntity> findReservedSeatByTicketId(@Param("ticketId") @NotNull UUID ticketId);

}
