package com.richglez.hotel.reservations.controller;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest // load beans, services, repo, security, config
@AutoConfigureMockMvc // Spring prepara el simulador HTTP.
@ActiveProfiles("test") // usa application-test.properties
public class ReservationControllerIntegrationTest {

    // Inject dependencies contructor
    @Autowired
    private MockMvc mockMvc; // Simulates real HTTP without open a real server

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldReturnReservations() throws Exception {

        mockMvc.perform(get("/api/reservations")) // fake request
                .andExpect(status().isOk()); // valida el status espero HTTP 200
    }
}

//Cuando hago GET /api/reservations
//la API debe responder 200 OK
