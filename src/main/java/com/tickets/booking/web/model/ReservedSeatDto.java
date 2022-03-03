package com.tickets.booking.web.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tickets.booking.domain.FlightEntity;
import com.tickets.booking.domain.TicketEntity;
import lombok.*;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ReservedSeatDto implements Serializable {

    @Valid
    @JsonProperty("id")
    private UUID id;

    @Valid
    @JsonProperty("flightId")
    private UUID flightId;

    @Valid
    @JsonProperty("ticketId")
    private UUID ticketId;

    @JsonProperty("seatNumber")
    private String seatNumber;
}
