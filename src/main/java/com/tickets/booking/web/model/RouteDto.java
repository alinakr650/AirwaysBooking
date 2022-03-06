package com.tickets.booking.web.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.Valid;
import java.io.Serializable;
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
