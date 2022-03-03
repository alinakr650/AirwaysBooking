package com.tickets.booking.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "flight")
public class FlightEntity implements Serializable {

    private static final long serialVersionUID = 2221L;

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "flight_id", columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

    @NotBlank
    @Column(name = "flight_number")
    private String flightNumber;

    @Column(name = "plane_name")
    @NotBlank
    String planeName;

    @Column(name = "time_of_departure")
    private LocalTime timeOfDeparture;

    @Column(name = "time_of_arrival")
    private LocalTime timeOfArrival;

    @Column(name = "date_of_arrival")
    private LocalDate dateOfArrival;

    @Column(name = "date_of_departure")
    private LocalDate dateOfDeparture;

    @Column(name = "flight_duration")
    private LocalTime flightDuration;

    @Column(name = "price")
    @Positive
    private BigDecimal price;

    @EqualsAndHashCode.Exclude
    @ManyToOne(optional = false, targetEntity = RouteEntity.class)
    @JoinColumn(name = "route_id")
    private RouteEntity routeEntity;

    @OneToMany(mappedBy = "flightEntity", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @ToString.Exclude
    private List<TicketEntity> ticketEntities = new ArrayList<>();

    @PreUpdate
    @PrePersist
    public void prePersist() {
        Duration diff = Duration.between(timeOfDeparture, timeOfArrival);
        int hours = (int) diff.toHours();
        int minutes = diff.toMinutesPart();
        this.setFlightDuration(LocalTime.of(hours, minutes));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        FlightEntity flightEntity = (FlightEntity) o;
        return id != null && Objects.equals(id, flightEntity.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}