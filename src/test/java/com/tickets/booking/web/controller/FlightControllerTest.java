package com.tickets.booking.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tickets.booking.domain.FlightEntity;
import com.tickets.booking.domain.RouteEntity;
import com.tickets.booking.repository.security.AuthorityRepository;
import com.tickets.booking.repository.security.RoleRepository;
import com.tickets.booking.repository.security.UserRepository;
import com.tickets.booking.services.FlightService;
import com.tickets.booking.web.model.FlightDto;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class FlightControllerTest {

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

    public UUID uuid;

    public MockMvc mockMvc;

    @BeforeEach
    public void setUp() {

        final String id = "493410b3-dd0b-4b78-97bf-289f50f6e74f";
        uuid = UUID.fromString(id);

        mockMvc = MockMvcBuilders
                .webAppContextSetup(wac)
                .apply(springSecurity())
                .build();
    }

    @MockBean
    FlightService flightService;

    @Test
    @WithUserDetails("IreneAdler")
    void searchThroughFlightsUser() throws Exception {
        String origin = "London";
        String destination = "Cardiff";
        LocalDate dateOfDeparture = LocalDate.of(2022, 3, 21);
        BigDecimal priceLimit = new BigDecimal(200.50);

        mockMvc.perform(get("/flights/search")
                        .param("origin", origin)
                        .param("destination", destination)
                        .param("dateOfDeparture", dateOfDeparture.toString())
                        .param("priceLimit", priceLimit.toString())
                        .param("orderByFlightDurationAsc", "T")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @WithUserDetails("alinakr650@gmail.com")
    void searchThroughFlightsAdmin() throws Exception {
        String origin = "London";
        String destination = "Cardiff";
        LocalDate dateOfDeparture = LocalDate.of(2022, 3, 21);
        BigDecimal priceLimit = new BigDecimal(200.50);

        mockMvc.perform(get("/flights/search")
                        .param("origin", origin)
                        .param("destination", destination)
                        .param("dateOfDeparture", dateOfDeparture.toString())
                        .param("priceLimit", priceLimit.toString())
                        .param("orderByFlightDurationAsc", "T")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    @WithUserDetails("jimMoriarty@gmail.com")
    @Test
    void findFlightByIdPassenger() throws Exception {

        when(flightService.findById(uuid)).thenReturn(Optional.of(getValidFlightEntity()));
        mockMvc.perform(get("/flights/" + getValidFlightEntity().getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    @WithUserDetails("alinakr650@gmail.com")
    @Test
    void findFlightByIdAdmin() throws Exception {

        when(flightService.findById(uuid)).thenReturn(Optional.of(getValidFlightEntity()));
        mockMvc.perform(get("/flights/" + getValidFlightEntity().getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    @WithUserDetails("alinakr650@gmail.com")
    @Test
    void createNewFlightAdmin() throws Exception {

        FlightDto flightDto = getValidFlightDto();
        String flightDtoJson = objectMapper.writeValueAsString(flightDto);

        mockMvc.perform(post("/flights")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(flightDtoJson))
                .andExpect(status().isCreated());

    }

    @WithUserDetails("IreneAdler")
    @Test
    void createNewFlightUser() throws Exception {

        FlightDto flightDto = getValidFlightDto();
        String flightDtoJson = objectMapper.writeValueAsString(flightDto);

        mockMvc.perform(post("/flights")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(flightDtoJson))
                .andExpect(status().is4xxClientError());

    }

    @WithUserDetails("alinakr650@gmail.com")
    @Test
    void updateFlightAdmin() throws Exception {

        given(flightService.updateFlight(any(FlightDto.class))).willReturn(getValidFlightDto());

        FlightDto flightDto = getValidFlightDto();
        String flightDtoJson = objectMapper.writeValueAsString(flightDto);

        mockMvc.perform(put("/flights/" + flightDto.getId().toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(flightDtoJson))
                .andExpect(status().is2xxSuccessful());
    }

    FlightDto getValidFlightDto() {
        return FlightDto.builder()
                .id(uuid)
                .routeId(new RouteEntity().getId())
                .flightNumber("NY349304409")
                .planeName("BOEING_717")
                .timeOfDeparture(LocalTime.of(12, 0, 0))
                .timeOfArrival(LocalTime.of(14, 13, 0))
                .dateOfDeparture(LocalDate.of(2022, 11, 8))
                .dateOfArrival(LocalDate.of(2022, 11, 8))
                .build();
    }

    public FlightEntity getValidFlightEntity() {
        return FlightEntity.builder()
                .id(uuid)
                .routeEntity(new RouteEntity())
                .flightNumber("NY349304409")
                .planeName("BOEING_717")
                .timeOfDeparture(LocalTime.of(12, 0, 0))
                .timeOfArrival(LocalTime.of(14, 13, 0))
                .dateOfDeparture(LocalDate.of(2022, 11, 8))
                .dateOfArrival(LocalDate.of(2022, 11, 8))
                .build();
    }
}

//        mockMvc.perform(post("/flights/")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(asJsonString(flightDto)))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.", equalTo("")))
//

//        String response = mockMvc.perform(post("/api/v1/customers/")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(asJsonString(customer)))
//                .andReturn().getResponse().getContentAsString();
//
//        System.out.println(response);