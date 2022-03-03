package com.tickets.booking.web.mappers;

import com.tickets.booking.domain.PassengerEntity;
import com.tickets.booking.domain.nationalities.Nationality;
import com.tickets.booking.repository.PassengerRepository;
import com.tickets.booking.web.model.PassengerDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;


import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
public class PassengerMapperTest {

    @Autowired
    PassengerRepository passengerRepository;

    PassengerEntity passengerEntity;

    @BeforeEach
    public void setUp(){
        passengerEntity = passengerRepository.findAll().get(0);
    }

    PassengerMapper passengerMapper = PassengerMapper.PASSENGER_MAPPER_INSTANCE;
    
    UUID id = UUID.randomUUID();

    @Test
    public void passengerDTOToPassenger() {

        PassengerDto passengerDto = new PassengerDto();
        passengerDto.setLastName(passengerEntity.getLastName());
        passengerDto.setMiddleName(passengerEntity.getMiddleName());
        passengerDto.setFirstName(passengerEntity.getFirstName());
        passengerDto.setPassportNumber(passengerEntity.getPassportNumber());
        passengerDto.setId(passengerEntity.getId());
        passengerDto.setNationality(passengerEntity.getNationality());

        PassengerEntity passenger = passengerMapper.passengerDtoToPassenger(passengerDto);

        assertEquals(passengerDto.getId(), passenger.getId());
        assertEquals(passengerDto.getFirstName(), passenger.getFirstName());
        assertEquals(passengerDto.getMiddleName(), passenger.getMiddleName());
        assertEquals(passengerDto.getLastName(), passenger.getLastName());
        assertEquals(passengerDto.getPassportNumber(), passenger.getPassportNumber());
        assertEquals(passengerDto.getNationality(), passenger.getNationality());
    }

    @Test
    public void passengerToPassengerDTO() {

        PassengerEntity passenger = new PassengerEntity();
        passenger.setLastName("Watson");
        passenger.setMiddleName("Hamish");
        passenger.setFirstName("John");
        passenger.setPassportNumber("FY839849340");
        passenger.setId(id);
        passenger.setNationality(Nationality.UK);

        PassengerDto passengerDto = passengerMapper.passengerToPassengerDto(passenger);

        assertEquals(id, passenger.getId());
        assertEquals(passengerDto.getFirstName(), passenger.getFirstName());
        assertEquals(passengerDto.getMiddleName(), passenger.getMiddleName());
        assertEquals(passengerDto.getLastName(), passenger.getLastName());
        assertEquals(passengerDto.getPassportNumber(), passenger.getPassportNumber());
        assertEquals(passengerDto.getNationality(), passenger.getNationality());
    }

    @Test
    public void updatePassengerFromDto(){

        PassengerDto passengerDto = new PassengerDto();
        passengerDto.setLastName(passengerEntity.getLastName());
        passengerDto.setMiddleName(passengerEntity.getMiddleName());
        passengerDto.setFirstName(passengerEntity.getFirstName());
        passengerDto.setPassportNumber(passengerEntity.getPassportNumber());
        passengerDto.setId(passengerEntity.getId());
        passengerDto.setNationality(passengerEntity.getNationality());

        PassengerEntity passenger = new PassengerEntity();

        passengerMapper.updatePassengerFromDto(passengerDto, passenger);

        assertEquals(passengerDto.getId(), passenger.getId());
        assertEquals(passengerDto.getFirstName(), passenger.getFirstName());
        assertEquals(passengerDto.getMiddleName(), passenger.getMiddleName());
        assertEquals(passengerDto.getLastName(), passenger.getLastName());
        assertEquals(passengerDto.getNationality(), passenger.getNationality());
        assertEquals(passengerDto.getPassportNumber(), passenger.getPassportNumber());
    }
}
