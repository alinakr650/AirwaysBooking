package com.tickets.booking.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "route")
public class RouteEntity implements Serializable {

    private static final long serialVersionUID = 1211L;

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "route_id", columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

    @OneToMany(mappedBy = "routeEntity", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @ToString.Exclude
    private List<FlightEntity> flights = new ArrayList<>();

    @Column(name = "origin")
    private String origin;

    @Column(name = "destination")
    private String destination;

    @Column(name = "flight_number")
    private String flightNumber;

    public void addFlight(FlightEntity flightEntity) {
        flights.add(flightEntity);
        flightEntity.setRouteEntity(this);
    }

    public void removeFlight(FlightEntity flightEntity) {
        flights.remove(flightEntity);
        flightEntity.setRouteEntity(null);
    }

    @JsonManagedReference
    public List<FlightEntity> getFlights() {
        return flights;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        RouteEntity routeEntity = (RouteEntity) o;
        return id != null && Objects.equals(id, routeEntity.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
