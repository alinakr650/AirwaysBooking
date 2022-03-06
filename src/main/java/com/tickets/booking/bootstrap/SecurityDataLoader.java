package com.tickets.booking.bootstrap;

import com.tickets.booking.domain.*;
import com.tickets.booking.domain.nationalities.Nationality;
import com.tickets.booking.domain.security.Authority;
import com.tickets.booking.domain.security.Role;
import com.tickets.booking.domain.security.User;
import com.tickets.booking.repository.*;
import com.tickets.booking.repository.security.AuthorityRepository;
import com.tickets.booking.repository.security.RoleRepository;
import com.tickets.booking.repository.security.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class SecurityDataLoader implements CommandLineRunner {

    private final AuthorityRepository authorityRepository;
    private final PassengerRepository passengerRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final RouteRepository routeRepository;
    private final ReservedSeatRepository reservedSeatRepository;
    private final PasswordEncoder passwordEncoder;

    PassengerEntity jimMoriarty;
    PassengerEntity gregorMendel;

    @Override
    public void run(String... args) throws Exception {
        loadSecurityData();
        loadData();
    }

    @Transactional
    void loadSecurityData() {

        Authority createFlight = checkAndSaveAuthority("flight.create");
        Authority readFlight = checkAndSaveAuthority("flight.read");
        Authority updateFlight = checkAndSaveAuthority("flight.update");
        Authority deleteFlight = checkAndSaveAuthority("flight.delete");

        Authority createPassenger = checkAndSaveAuthority("passenger.create");
        Authority readPassenger = checkAndSaveAuthority("passenger.read");
        Authority readPassengerPassenger = checkAndSaveAuthority("passenger.passenger.read");
        Authority updatePassenger = checkAndSaveAuthority("passenger.update");
        Authority deletePassenger = checkAndSaveAuthority("passenger.delete");

        Authority createRoute = checkAndSaveAuthority("route.create");
        Authority readRoute = checkAndSaveAuthority("route.read");
        Authority updateRoute = checkAndSaveAuthority("route.update");

        Authority createTicket = checkAndSaveAuthority("ticket.create");
        Authority readTicket = checkAndSaveAuthority("ticket.read");
        Authority updateTicket = checkAndSaveAuthority("ticket.update");
        Authority deleteTicket = checkAndSaveAuthority("ticket.delete");
        Authority createPassengerTicket = checkAndSaveAuthority("passenger.ticket.create");
        Authority readPassengerTicket = checkAndSaveAuthority("passenger.ticket.read");
        Authority updatePassengerTicket = checkAndSaveAuthority("passenger.ticket.update");
        Authority deletePassengerTicket = checkAndSaveAuthority("passenger.ticket.delete");

        Authority reserveSeat = checkAndSaveAuthority("seat.reserve");
        Authority displayAllSeats = checkAndSaveAuthority("seat.display");
        Authority changeSeat = checkAndSaveAuthority("seat.change");
        Authority passengerReserveSeat = checkAndSaveAuthority("passenger.seat.reserve");
        Authority passengerDisplayAllSeats = checkAndSaveAuthority("passenger.seat.display");
        Authority passengerChangeSeat = checkAndSaveAuthority("passenger.seat.change");

        Role adminRole = checkAndSaveRole("ADMIN");
        Role passengerRole = checkAndSaveRole("PASSENGER");
        Role userRole = checkAndSaveRole("USER");

        adminRole.setAuthorities(new HashSet<>(Set.of(createFlight, updateFlight, readFlight, deleteFlight,
                createPassenger, readPassenger,
                updatePassenger, deletePassenger, createRoute, readRoute, updateRoute,
                createTicket, readTicket, updateTicket, deleteTicket,
                reserveSeat, displayAllSeats, changeSeat)));

        passengerRole.setAuthorities(new HashSet<>(Set.of(readFlight, readPassengerPassenger, readRoute,
                createPassengerTicket, readPassengerTicket,
                updatePassengerTicket, deletePassengerTicket,
                passengerDisplayAllSeats, passengerChangeSeat, passengerReserveSeat)));

        userRole.setAuthorities(new HashSet<>(Set.of(readFlight, readRoute)));

        if (adminRole != null) {
            roleRepository.save(adminRole);
        }
        if (passengerRole != null) {
            roleRepository.save(passengerRole);
        }
        if (userRole != null) {
            roleRepository.save(userRole);
        }

        jimMoriarty = checkAndSavePassenger("Jim", "Moriarty", "JM38984399"
                , Nationality.UK);
        gregorMendel = checkAndSavePassenger("Gregor", "Mendel", "GM483984934"
                , Nationality.Czech_Republic);

        checkAndSaveUser("alinakr650@gmail.com", "justMe1", adminRole);
        checkAndSaveUser("IreneAdler", "", userRole);
        checkAndSaveUser("jimMoriarty@gmail.com", "evilGenius1", passengerRole, jimMoriarty);
        checkAndSaveUser("gregor23@gmail.com", "genetics1", passengerRole, gregorMendel);


        log.debug("Users Loaded: " + userRepository.count());

    }

    private Authority checkAndSaveAuthority(String permission) {
        Optional<Authority> authority = authorityRepository.findAuthorityByPermission(permission);

        if (authority.isPresent()) {
            return authority.get();
        }
        return authorityRepository.save(Authority.builder().permission(permission).build());
    }

    private Role checkAndSaveRole(String name) {
        Optional<Role> role = roleRepository.findRoleByName(name);

        if (role.isPresent()) {
            return role.get();
        }
        return roleRepository.save(Role.builder().name(name).build());
    }

    private void checkAndSaveUser(String username, String password, Role role) {
        Optional<User> user = userRepository.findByUsername(username);

        if (!user.isPresent()) {
            userRepository.saveAndFlush(User.builder()
                    .username(username)
                    .password(passwordEncoder.encode(password))
                    .role(role)
                    .build());
        }
    }

    private void checkAndSaveUser(String username, String password, Role role, PassengerEntity passenger) {
        Optional<User> user = userRepository.findByUsername(username);

        if (!user.isPresent()) {
            userRepository.saveAndFlush(User.builder()
                    .username(username)
                    .password(passwordEncoder.encode(password))
                    .passenger(passenger)
                    .role(role)
                    .build());
        }
    }

    private PassengerEntity checkAndSavePassenger(String firstname, String lastname, String passportNumber, Nationality nationality) {
        Optional<PassengerEntity> passenger = passengerRepository
                .findPassengerEntityByFirstNameAndLastNameAndPassportNumberAndNationality(firstname,
                        lastname, passportNumber, nationality);

        if (!passenger.isPresent()) {
            PassengerEntity newPassenger = passengerRepository.saveAndFlush(PassengerEntity.builder()
                    .firstName(firstname)
                    .lastName(lastname)
                    .passportNumber(passportNumber)
                    .nationality(nationality)
                    .build());

            return newPassenger;
        }

        return passenger.get();
    }

    @Transactional
    void loadData() {
        if (routeRepository.count() == 0) {
            RouteEntity routeEntity = new RouteEntity();
            routeEntity.setOrigin("Lyon");
            routeEntity.setDestination("Nice");
            routeEntity.setFlightNumber("NI28932794");

            RouteEntity routeEntity1 = new RouteEntity();
            routeEntity1.setOrigin("Nice");
            routeEntity1.setDestination("Lyon");
            routeEntity1.setFlightNumber("LY93943280");

            routeRepository.saveAndFlush(routeEntity1);
            routeRepository.saveAndFlush(routeEntity);

            FlightEntity flightEntity = new FlightEntity();
            flightEntity.setRouteEntity(routeEntity);
            flightEntity.setPlaneName("BOEING_737_800");
            flightEntity.setFlightNumber(routeEntity.getFlightNumber());
            flightEntity.setTimeOfArrival(LocalTime.of(17, 03, 0));
            flightEntity.setTimeOfDeparture(LocalTime.of(14, 32, 0));
            flightEntity.setDateOfArrival(LocalDate.of(2022, 1, 3));
            flightEntity.setDateOfDeparture(LocalDate.of(2022, 1, 3));
            flightEntity.setPrice(new BigDecimal("103.75"));

            FlightEntity flightEntity1 = new FlightEntity();
            flightEntity1.setRouteEntity(routeEntity1);
            flightEntity1.setPlaneName("AIRBUS_A320");
            flightEntity1.setFlightNumber(routeEntity1.getFlightNumber());
            flightEntity1.setTimeOfArrival(LocalTime.of(11, 20, 0));
            flightEntity1.setTimeOfDeparture(LocalTime.of(8, 40, 0));
            flightEntity1.setDateOfArrival(LocalDate.of(2022, 6, 22));
            flightEntity1.setDateOfDeparture(LocalDate.of(2022, 6, 22));
            flightEntity1.setPrice(new BigDecimal("40.10"));

            PassengerEntity passengerEntity1 = passengerRepository
                    .findPassengerEntityByFirstNameAndLastNameAndPassportNumberAndNationality("Jim",
                            "Moriarty", "JM38984399", Nationality.UK).get();

            PassengerEntity passengerEntity2 = passengerRepository
                    .findPassengerEntityByFirstNameAndLastNameAndPassportNumberAndNationality("Gregor",
                            "Mendel", "GM483984934", Nationality.Czech_Republic).get();


            TicketEntity ticketEntity1 = new TicketEntity();
            ticketEntity1.setActive(true);
            ticketEntity1.setFlightEntity(flightEntity);
            ticketEntity1.setPassengerEntity(passengerEntity1);

            TicketEntity ticketEntity2 = new TicketEntity();
            ticketEntity2.setActive(true);
            ticketEntity2.setFlightEntity(flightEntity1);
            ticketEntity2.setPassengerEntity(passengerEntity2);

            flightEntity.setTicketEntities(List.of(ticketEntity1));
            flightEntity1.setTicketEntities(List.of(ticketEntity2));

            ReservedSeatEntity reservedSeatEntity = new ReservedSeatEntity();
            reservedSeatEntity.setFlightEntity(flightEntity);
            reservedSeatEntity.setTicketEntity(ticketEntity1);
            reservedSeatEntity.setSeatNumber("C8");

            ReservedSeatEntity reservedSeatEntity1 = new ReservedSeatEntity();
            reservedSeatEntity1.setFlightEntity(flightEntity1);
            reservedSeatEntity1.setTicketEntity(ticketEntity2);
            reservedSeatEntity1.setSeatNumber("F3");

            reservedSeatRepository.save(reservedSeatEntity);
            reservedSeatRepository.save(reservedSeatEntity1);
        }
    }
}
