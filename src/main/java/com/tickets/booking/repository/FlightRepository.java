package com.tickets.booking.repository;

import com.tickets.booking.domain.FlightEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Validated
@Repository
public interface FlightRepository extends JpaRepository<FlightEntity, UUID> {

    @Query(value = "SELECT fe FROM FlightEntity fe WHERE fe.routeEntity.origin = :origin AND fe.dateOfDeparture =:dateOfDeparture")
    List<FlightEntity> findFlightsByDepartureDateAndOrigin(@Param("origin") @NotBlank String origin,
                                                           @Param("dateOfDeparture") @NotNull LocalDate dateOfDeparture);

    @Query(value = "SELECT fe FROM FlightEntity fe WHERE fe.routeEntity.origin = :origin AND fe.dateOfDeparture =:dateOfDeparture AND fe.routeEntity.destination =:destination AND fe.timeOfDeparture =:timeOfDeparture")
    List<FlightEntity> findFlightsByDepartureDateAndOriginAndDestination(@Param("dateOfDeparture") @NotNull LocalDate dateOfDeparture,
                                                                         @Param("origin") @NotBlank String origin,
                                                                         @Param("destination") @NotBlank String destination,
                                                                         @Param("timeOfDeparture") @NotNull LocalTime timeOfDeparture);

    @Query("SELECT fe FROM FlightEntity fe WHERE fe.id IN :ids")
    List<FlightEntity> findFlightEntitiesById(@Param("ids") List<UUID> ids);

    @Query("SELECT fe FROM FlightEntity fe WHERE fe.routeEntity.id = :routeId AND fe.dateOfDeparture = :dateOfDeparture " +
            "AND fe.timeOfDeparture = :timeOfDeparture")
    Optional<FlightEntity> findFlightEntityByRouteIdAndDepartureDateAndDepartureTime(@Param("routeId") UUID routeId,
                                                                                     @Param("dateOfDeparture") @NotNull LocalDate dateOfDeparture,
                                                                                     @Param("timeOfDeparture") @NotNull LocalTime timeOfDeparture);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = "DELETE FROM flight WHERE flight_id = ?1", nativeQuery = true)
    void deleteById(UUID flightId);

}