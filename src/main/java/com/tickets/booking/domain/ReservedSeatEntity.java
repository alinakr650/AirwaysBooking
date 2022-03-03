package com.tickets.booking.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "reserved_seats")
public class ReservedSeatEntity {

    private static final long serialVersionUID = 2221L;

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "reserved_seat_id", columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name= "flight_id")
    FlightEntity flightEntity;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name= "ticket_id")
    TicketEntity ticketEntity;

    @Column(name = "seat_number")
    String seatNumber;

}

