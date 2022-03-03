package com.tickets.booking.web.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tickets.booking.domain.nationalities.Nationality;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TicketRequisite {

    @JsonProperty("flightId")
    @NotNull
    private UUID flightId;

    @JsonProperty("existingTicketId")
    private UUID existingTicketId;

    @Valid
    @Size(min = 2, message = "First name should have at least 2 characters")
    @JsonProperty("passengersFirstName")
    private String passengersFirstName;

    @Valid
    @JsonProperty("passengersMiddleName")
    private String passengersMiddleName;

    @Valid
    @Size(min = 2, message = "Last name should have at least 2 characters")
    @JsonProperty("passengersLastName")
    private String passengersLastName;

    @JsonProperty("passengersPassportNumber")
    @NotBlank
    private String passengersPassportNumber;

    @JsonProperty("passengersNationality")
    @NotNull
    private Nationality passengersNationality;

}
