package com.tickets.booking.services.impl;

import com.tickets.booking.domain.PassengerEntity;
import com.tickets.booking.domain.TicketEntity;
import com.tickets.booking.domain.nationalities.Nationality;
import com.tickets.booking.repository.PassengerRepository;
import com.tickets.booking.services.PassengerService;
import com.tickets.booking.services.exceptions.PassengerNotFoundException;
import com.tickets.booking.web.mappers.PassengerMapper;
import com.tickets.booking.web.mappers.TicketMapper;
import com.tickets.booking.web.model.PassengerDto;
import com.tickets.booking.web.model.TicketDto;
import com.tickets.booking.web.model.TicketRequisite;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PassengerServiceImpl implements PassengerService {

    private final PassengerRepository passengerRepository;
    private final PassengerMapper passengerMapper;
    private final TicketMapper ticketMapper;

    public PassengerServiceImpl(PassengerRepository passengerRepository, PassengerMapper passengerMapper, TicketMapper ticketMapper) {
        this.passengerRepository = passengerRepository;
        this.passengerMapper = passengerMapper;
        this.ticketMapper = ticketMapper;
    }

    @Override
    public List<TicketDto> findTicketsByPassenger(UUID passengerId) {
        List<TicketEntity> tickets = passengerRepository.findAllTicketsByPassengerId(passengerId);
        return tickets
                .stream()
                .map(ticketMapper::ticketToTicketDto)
                .collect(Collectors.toList());
    }

    @Override
    public PassengerDto createNewPassenger(PassengerDto passengerDto) {

        return saveAndReturnDto(passengerMapper.passengerDtoToPassenger(passengerDto));
    }

    @Override
    public PassengerDto savePassengerByDto(PassengerDto passengerDto) {
        PassengerEntity passenger = passengerMapper.passengerDtoToPassenger(passengerDto);
        return saveAndReturnDto(passenger);
    }

    @Override
    public void updatePassenger(PassengerDto passengerDto) {
        PassengerEntity passengerEntity = passengerRepository.findById(passengerDto.getId())
                .orElseThrow(PassengerNotFoundException::new);
        passengerMapper.updatePassengerFromDto(passengerDto, passengerEntity);
        passengerRepository.save(passengerEntity);
    }

    @Override
    public PassengerEntity updatePassengerByRequisites(TicketRequisite ticketRequisite, PassengerEntity passengerEntity) {
        passengerEntity.setNationality(ticketRequisite.getPassengersNationality());
        passengerEntity.setFirstName(ticketRequisite.getPassengersFirstName());
        passengerEntity.setMiddleName(ticketRequisite.getPassengersMiddleName());
        passengerEntity.setLastName(ticketRequisite.getPassengersLastName());
        passengerEntity.setPassportNumber(ticketRequisite.getPassengersPassportNumber());
        return passengerEntity;
    }

    @Override
    public void deletePassengerById(UUID id) {
        if (id != null) {
            passengerRepository.deleteById(id);
        }
    }

    @Override
    public void savePassenger(PassengerEntity passengerEntity) {
        passengerRepository.save(passengerEntity);
    }

    @Override
    public PassengerEntity savePassenger(TicketRequisite ticketRequisite) {
        PassengerEntity passenger = PassengerEntity.builder()
                .firstName(ticketRequisite.getPassengersFirstName())
                .middleName(ticketRequisite.getPassengersMiddleName())
                .lastName(ticketRequisite.getPassengersLastName())
                .passportNumber(ticketRequisite.getPassengersPassportNumber())
                .nationality(ticketRequisite.getPassengersNationality())
                .build();
        passengerRepository.save(passenger);
        return passenger;
    }

    @Override
    public PassengerEntity findPassengerEntityById(UUID id) {
        return passengerRepository.findPassengerEntityById(id).orElseThrow(PassengerNotFoundException::new);
    }

    @Override
    public Optional<PassengerEntity> findPassengerEntityByIdOptional(UUID id) {
        return passengerRepository.findPassengerEntityById(id);
    }

    @Override
    public Optional<PassengerEntity> findPassengerByFirstNameAndLastNameAndPassportNumberAndNationality(String firstName,
                                                                                                        String lastName, String passportNumber,
                                                                                                        Nationality nationality) {
        return passengerRepository.findPassengerEntityByFirstNameAndLastNameAndPassportNumberAndNationality(firstName,
                lastName, passportNumber, nationality);
    }


    @Override
    public PassengerDto findPassengerById(UUID id) {

        return passengerRepository.findById(id)
                .map(passengerMapper::passengerToPassengerDto)
                .orElseThrow(PassengerNotFoundException::new);
    }

    private PassengerDto saveAndReturnDto(PassengerEntity passengerEntity) {
        PassengerEntity savedPassenger = passengerRepository.save(passengerEntity);
        return passengerMapper.passengerToPassengerDto(savedPassenger);
    }
}


