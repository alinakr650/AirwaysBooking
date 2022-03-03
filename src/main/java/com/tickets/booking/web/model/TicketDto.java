package com.tickets.booking.web.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class TicketDto implements Serializable {

    @Valid
    @JsonProperty("id")
    @JsonIgnore
    private UUID id;

    @Valid
    @JsonProperty("passengerId")
    private UUID passengerId;

    @Valid
    @JsonProperty("flightId")
    private UUID flightId;

    @JsonIgnore
    @JsonProperty("isActive")
    @Builder.Default
    private boolean isActive = true;
}
