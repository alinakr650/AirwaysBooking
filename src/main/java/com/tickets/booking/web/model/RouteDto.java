package com.tickets.booking.web.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tickets.booking.domain.FlightEntity;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.Valid;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RouteDto implements Serializable {

    @Valid
    @JsonProperty("id")
    private UUID id;

    @JsonProperty("origin")
    private String origin;

    @JsonProperty("destination")
    private String destination;

    @JsonProperty("flightNumber")
    private String flightNumber;
}
