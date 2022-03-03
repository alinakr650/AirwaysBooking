package com.tickets.booking.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tickets.booking.domain.PassengerEntity;
import com.tickets.booking.domain.nationalities.Nationality;
import com.tickets.booking.repository.PassengerRepository;
import com.tickets.booking.repository.security.AuthorityRepository;
import com.tickets.booking.repository.security.RoleRepository;
import com.tickets.booking.repository.security.UserRepository;
import com.tickets.booking.services.PassengerService;
import com.tickets.booking.services.exceptions.PassengerNotFoundException;
import com.tickets.booking.web.mappers.PassengerMapper;
import com.tickets.booking.web.model.PassengerDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.UUID;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class PassengerControllerTest {

    @Autowired
    public WebApplicationContext wac;

    @Autowired
    public AuthorityRepository authorityRepository;

    @Autowired
    public RoleRepository roleRepository;

    @Autowired
    public UserRepository userRepository;

    @Autowired
    public ObjectMapper objectMapper;

    @Autowired
    PassengerRepository passengerRepository;

    @Autowired
    PassengerMapper passengerMapper;

    public UUID uuid;

    public MockMvc mockMvc;

    PassengerEntity jimMoriarty;
    PassengerEntity gregorMendel;

    PassengerDto passengerDto;

    @BeforeEach
    public void setUp() {

        final String id = "493410b3-dd0b-4b78-97bf-289f50f6e74f";
        uuid = UUID.fromString(id);

        passengerDto = passengerMapper.passengerToPassengerDto(passengerRepository.findAll().get(0));

        jimMoriarty = passengerService
                .findPassengerByFirstNameAndLastNameAndPassportNumberAndNationality("Jim", "Moriarty",
                        "JM38984399", Nationality.UK)
                .orElseThrow(PassengerNotFoundException::new);

        gregorMendel = passengerService
                .findPassengerByFirstNameAndLastNameAndPassportNumberAndNationality("Gregor", "Mendel",
                        "GM483984934", Nationality.Czech_Republic)
                .orElseThrow(PassengerNotFoundException::new);

        mockMvc = MockMvcBuilders
                .webAppContextSetup(wac)
                .apply(springSecurity())
                .build();
    }

    @Autowired
    PassengerService passengerService;

    @WithUserDetails("JimMoriarty")
    @Test
    void findPassengerByRightPassengerId() throws Exception {
        mockMvc.perform(get("/passengers/" + jimMoriarty.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    @WithUserDetails("JimMoriarty")
    @Test
    void findPassengerByWrongPassengerId() throws Exception {
        mockMvc.perform(get("/passengers/" + gregorMendel.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @WithUserDetails("alinakr650")
    @Test
    void createNewPassengerAdmin() throws Exception{
        PassengerDto passengerDto = getValidPassengerDto();
        String passengerJson = objectMapper.writeValueAsString(passengerDto);

        mockMvc.perform(post("/passengers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(passengerJson))
                .andExpect(status().isCreated());
    }

    @WithUserDetails("gregor23")
    @Test
    void createNewPassengerByPassenger() throws Exception{
        PassengerDto passengerDto = getValidPassengerDto();
        String passengerJson = objectMapper.writeValueAsString(passengerDto);

        mockMvc.perform(post("/passengers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(passengerJson))
                .andExpect(status().isForbidden());
    }

    @WithUserDetails("alinakr650")
    @Test
    void updatePassengerAdmin() throws Exception{

        PassengerDto passengerDto = passengerMapper.passengerToPassengerDto(passengerRepository.findAll().get(0));
        String passengerDtoJson = objectMapper.writeValueAsString(passengerDto);

        mockMvc.perform(put("/passengers/" + passengerDto.getId().toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(passengerDtoJson))
                .andExpect(status().is2xxSuccessful());
    }

    @WithUserDetails("IreneAdler")
    @Test
    void updatePassengerByUser() throws Exception{

        String passengerDtoJson = objectMapper.writeValueAsString(passengerDto);

        mockMvc.perform(put("/passengers/" + passengerDto.getId().toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(passengerDtoJson))
                .andExpect(status().isForbidden());
    }

    @WithUserDetails("JimMoriarty")
    @Test
    void deletePassengerByPassenger() throws Exception{

        mockMvc.perform(delete("/passengers/" + passengerDto.getId().toString()))
                .andExpect(status().isForbidden());
    }

    @Rollback
    @Transactional
    @WithUserDetails("alinakr650")
    @Test
    void deletePassengerAdmin() throws Exception{

        mockMvc.perform(delete("/passengers/" + passengerDto.getId().toString()))
                .andExpect(status().isOk());
    }

    public PassengerDto getValidPassengerDto() {
        return PassengerDto.builder()
                .id(uuid)
                .nationality(Nationality.UK)
                .firstName("Mycroft")
                .lastName("Holmes")
                .passportNumber("FK39403434")
                .build();
    }
}