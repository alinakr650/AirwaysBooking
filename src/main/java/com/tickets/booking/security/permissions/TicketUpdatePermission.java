package com.tickets.booking.security.permissions;


import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("hasAuthority('ticket.update') OR " +
        "hasAuthority('passenger.ticket.update') " +
        " AND @ticketPurchaseAuthenticationManager.passengerIdMatches(authentication, #passengerId )")
public @interface TicketUpdatePermission {
}
