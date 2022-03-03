package com.tickets.booking.services;

import com.tickets.booking.domain.PassengerEntity;
import com.tickets.booking.domain.nationalities.Nationality;
import com.tickets.booking.web.model.PassengerDto;
import com.tickets.booking.web.model.TicketDto;
import com.tickets.booking.web.model.TicketRequisite;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Transactional
public interface PassengerService {

    void savePassenger(PassengerEntity passengerEntity);

    Optional<PassengerEntity> findPassengerByFirstNameAndLastNameAndPassportNumberAndNationality(String firstName, String lastName, String passportNumber, Nationality nationality);

    PassengerDto findPassengerById(UUID id);

    PassengerEntity savePassenger(TicketRequisite ticketRequisite);

    PassengerEntity findPassengerEntityById(UUID id);

    Optional<PassengerEntity> findPassengerEntityByIdOptional(UUID id);

    void deletePassengerById(UUID id);

    PassengerEntity updatePassengerByRequisites(TicketRequisite ticketRequisite, PassengerEntity passengerEntity);

    void updatePassenger(PassengerDto passengerDto);

    PassengerDto createNewPassenger(PassengerDto passengerDto);

    PassengerDto savePassengerByDto(PassengerDto passengerDto);

    List<TicketDto> findTicketsByPassenger(UUID passengerId);
}
