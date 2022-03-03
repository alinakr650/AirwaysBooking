package com.tickets.booking.security.permissions;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("hasAnyAuthority('seat.change', 'passenger.seat.change')")
public @interface SeatChangePermission {
}
