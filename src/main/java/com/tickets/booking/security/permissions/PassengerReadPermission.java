package com.tickets.booking.security.permissions;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("hasAuthority('passenger.read') OR " +
        "hasAuthority('passenger.passenger.read') " +
        " AND @ticketPurchaseAuthenticationManager.passengerIdMatches(authentication, #passengerId)")
public @interface PassengerReadPermission {
}
