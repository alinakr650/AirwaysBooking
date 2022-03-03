package com.tickets.booking.repository;

import com.tickets.booking.domain.FlightEntity;
import com.tickets.booking.domain.RouteEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Validated
public interface RouteRepository extends JpaRepository<RouteEntity, UUID> {

    Optional<RouteEntity> findRouteEntityByOriginAndDestination(@NotBlank String origin, @NotBlank String destination);

    Page<RouteEntity> findAll(Pageable pageable);

    @Query("SELECT re FROM RouteEntity re WHERE re.id = :routeId")
    RouteEntity findRouteEntityById(@Param("routeId") UUID routeId);

    @Query("SELECT fe FROM FlightEntity fe WHERE fe.routeEntity.id =?1")
    List<FlightEntity> findFlightEntitiesByRouteId(UUID routeId);

    @Query("SELECT re FROM RouteEntity re WHERE re.flightNumber =?1")
    RouteEntity findRouteEntityByFlightNumber(String flightNumber);
}
