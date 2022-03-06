package com.tickets.booking.web.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.Valid;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class FlightDto implements Serializable {

    @Valid
    @JsonProperty("id")
    private UUID id;

    @JsonProperty("flightNumber")
    private String flightNumber;

    @JsonProperty("planeName")
    private String planeName;

    @JsonProperty("timeOfDeparture")
    private LocalTime timeOfDeparture;

    @JsonProperty("timeOfArrival")
    private LocalTime timeOfArrival;

    @JsonProperty("dateOfDeparture")
    private LocalDate dateOfDeparture;

    @JsonProperty("dateOfArrival")
    private LocalDate dateOfArrival;

    @JsonProperty("routeId")
    @JsonIgnore
    private UUID routeId;

    @JsonProperty("price")
    private BigDecimal price;

}
