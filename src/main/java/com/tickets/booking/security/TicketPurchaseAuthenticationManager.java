package com.tickets.booking.security;

import com.tickets.booking.domain.security.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
public class TicketPurchaseAuthenticationManager {

    public boolean passengerIdMatches(Authentication authentication, UUID passengerId){
        User authenticatedUser = (User) authentication.getPrincipal();

        log.debug("Auth User Passenger Id: " + authenticatedUser.getPassenger().getId() + " Passenger Id:" + passengerId);

        return authenticatedUser.getPassenger().getId().equals(passengerId);
    }

}
