package com.tickets.booking.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "ticket")
public class TicketEntity implements Serializable {

    private static final long serialVersionUID = 1555L;

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "ticket_id", columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

    @EqualsAndHashCode.Exclude
    @ManyToOne()
    @JoinTable(
            name = "passengers_tickets",
            joinColumns = {@JoinColumn(name = "ticket_id")},
            inverseJoinColumns = {@JoinColumn(name = "passenger_id", referencedColumnName = "id")}
    )
    private PassengerEntity passengerEntity;

    @EqualsAndHashCode.Exclude
    @ManyToOne(optional = false, cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "flight_id", updatable = false)
    private FlightEntity flightEntity;

    @Column(name = "active")
    @Builder.Default
    private boolean isActive = true;

    @PreRemove
    private void onDelete() {
        this.setPassengerEntity(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        TicketEntity ticketEntity = (TicketEntity) o;
        return id != null && Objects.equals(id, ticketEntity.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}