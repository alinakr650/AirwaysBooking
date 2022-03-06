package com.tickets.booking.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tickets.booking.domain.RouteEntity;
import com.tickets.booking.repository.security.AuthorityRepository;
import com.tickets.booking.repository.security.RoleRepository;
import com.tickets.booking.repository.security.UserRepository;
import com.tickets.booking.services.RouteService;
import com.tickets.booking.web.model.RouteDto;
import com.tickets.booking.web.model.RoutePagedList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class RouteControllerTest {

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
    RouteService routeService;

    @WithUserDetails("IreneAdler")
    @Test
    void listRoutesUser() throws Exception {
        given(routeService.findAllRoutes(PageRequest.of(0, 10)))
                .willReturn(new RoutePagedList(List.of(getValidRouteDto())));
        mockMvc.perform(get("/routes")
                        .param("pageNumber", String.valueOf(0))
                        .param("pageSize", String.valueOf(10)))
                .andExpect(status().isOk());

    }

    @WithUserDetails("jimMoriarty@gmail.com")
    @Test
    void findRouteByIdPassenger() throws Exception {
        when(routeService.findById(uuid)).thenReturn(Optional.of(getValidRouteEntity()));
        mockMvc.perform(get("/routes/" + getValidRouteEntity().getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    @WithUserDetails("alinakr650@gmail.com")
    @Test
    void createNewRouteAdmin() throws Exception {
        RouteDto routeDto = getValidRouteDto();
        String flightDtoJson = objectMapper.writeValueAsString(routeDto);

        mockMvc.perform(post("/routes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(flightDtoJson))
                .andExpect(status().isCreated());
    }

    @WithUserDetails("jimMoriarty@gmail.com")
    @Test
    void createNewRoutePassenger() throws Exception {
        RouteDto routeDto = getValidRouteDto();
        String flightDtoJson = objectMapper.writeValueAsString(routeDto);

        mockMvc.perform(post("/routes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(flightDtoJson))
                .andExpect(status().is4xxClientError());
    }

    @WithUserDetails("alinakr650@gmail.com")
    @Test
    void updateRouteAdmin() throws Exception {
        given(routeService.updateRoute(any(RouteDto.class))).willReturn(getValidRouteDto());

        RouteDto routeDto = getValidRouteDto();
        String flightDtoJson = objectMapper.writeValueAsString(routeDto);

        mockMvc.perform(put("/routes/" + routeDto.getId().toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(flightDtoJson))
                .andExpect(status().is2xxSuccessful());
    }

    public RouteEntity getValidRouteEntity() {
        return RouteEntity.builder()
                .id(uuid)
                .flightNumber("NY349304409")
                .origin("Milan")
                .destination("New York")
                .build();
    }

    public RouteDto getValidRouteDto() {
        return RouteDto.builder()
                .id(uuid)
                .flightNumber("NY349304409")
                .origin("Milan")
                .destination("New York")
                .build();
    }
}