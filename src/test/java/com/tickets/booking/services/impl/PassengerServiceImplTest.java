package com.tickets.booking.services.impl;

import com.tickets.booking.BaseTestClass;
import com.tickets.booking.domain.PassengerEntity;
import com.tickets.booking.domain.nationalities.Nationality;
import com.tickets.booking.services.PassengerService;
import com.tickets.booking.web.mappers.PassengerMapper;
import com.tickets.booking.web.model.PassengerDto;
import com.tickets.booking.web.model.TicketDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;

class PassengerServiceImplTest extends BaseTestClass {

    @Autowired
    PassengerMapper passengerMapper;

    @Autowired
    PassengerService passengerService;

    @BeforeEach
    void setUp(){
        super.saveEntities();
    }

    @Test
    void getAllTicketsByPassenger(){
        UUID id = passengerRepository.findAll().get(0).getId();
        List<TicketDto> tickets = passengerService.findTicketsByPassenger(id);

        assertThat(tickets.size()).isEqualTo(2);
    }

    @Test
    void findPassengerEntityByFirstNameAndLastNameAndPassportNumberAndNationality(){
        Optional<PassengerEntity> passengerEntity = passengerService.findPassengerByFirstNameAndLastNameAndPassportNumberAndNationality("John", "Watson", "JK8438084", Nationality.UK);

        assertThat(passengerEntity.get()).isNotNull();
        assertThat(passengerEntity.get().getNationality()).isEqualTo(Nationality.UK);
    }


    @Test
    void createNewPassengerEntity() {
        PassengerDto savedPassengerDto = passengerService.createNewPassenger(returnValidDto());

        assertThat(savedPassengerDto.getId()).isNotNull();
        assertThat(savedPassengerDto.getFirstName()).isEqualTo("Sherlock");
    }

    @Test
    void savePassengerByDto() {
        PassengerDto savedPassengerDto = passengerService.savePassengerByDto(returnValidDto());

        assertThat(savedPassengerDto.getId()).isNotNull();
        assertThat(savedPassengerDto.getFirstName()).isEqualTo("Sherlock");
    }

    @Test
    void updatePassengerEntity() {
        PassengerEntity passengerEntity = passengerRepository.findAll().get(0);
        UUID id = passengerEntity.getId();
        passengerEntity.setFirstName("Mycroft");

        passengerService.updatePassenger(passengerMapper.passengerToPassengerDto(passengerEntity));

        PassengerEntity updatedEntity = passengerRepository.findById(id).get();
        assertThat(updatedEntity.getFirstName()).isEqualTo("Mycroft");
    }

    @Test
    void findPassengerById() {
        PassengerEntity passengerEntity = passengerRepository.findAll().get(0);
        PassengerDto passengerDto = passengerService.findPassengerById(passengerEntity.getId());

        assertThat(passengerDto.getId()).isEqualTo(passengerEntity.getId());
    }

    @Test
    void findTicketsByPassenger(){
        PassengerEntity passengerEntity = passengerRepository.findAll().get(0);

        List<TicketDto> tickets = passengerService.findTicketsByPassenger(passengerEntity.getId());

        assertThat(tickets.size()).isEqualTo(2);

    }

    @Test
    void deletePassengerById() {
        PassengerEntity passengerEntity = passengerRepository.findAll().get(0);
        passengerService.deletePassengerById(passengerEntity.getId());

        assertThat(passengerRepository.findById(passengerEntity.getId())).isEmpty();

    }


    private PassengerDto returnValidDto() {
        PassengerDto passengerDto = new PassengerDto();
        passengerDto.setPassportNumber("AK8493494");
        passengerDto.setFirstName("Sherlock");
        passengerDto.setMiddleName("Scott");
        passengerDto.setLastName("Holmes");
        passengerDto.setNationality(Nationality.UK);
        return passengerDto;
    }
}