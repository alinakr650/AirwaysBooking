package com.tickets.booking.web.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tickets.booking.domain.nationalities.Nationality;
import lombok.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.Valid;
import java.io.Serializable;
import java.util.UUID;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PassengerDto implements Serializable {

    @Valid
    @JsonProperty("id")
    private UUID id;

    @Valid
    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("middleName")
    private String middleName;

    @Valid
    @JsonProperty("lastName")
    private String lastName;

    @JsonProperty("nationality")
    @Enumerated(EnumType.STRING)
    private Nationality nationality;

    @JsonProperty("passportNumber")
    private String passportNumber;
}
