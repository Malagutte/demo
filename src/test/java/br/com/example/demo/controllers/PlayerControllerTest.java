package br.com.example.demo.controllers;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import br.com.example.demo.dtos.PlayerDTO;
import br.com.example.demo.dtos.request.PlayerRequestDTO;
import br.com.example.demo.services.IPlayerService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(PlayerController.class)
public class PlayerControllerTest {

    @MockBean
    private IPlayerService playerService;

    @Autowired
    private MockMvc mockMvc;

    private final String API_ROUTE_BASE = "/api/v1";

    @Test
    public void whenSendPlayersShouldProcessWithouError() throws Exception {

        var mapper = new ObjectMapper();
        var players = new ArrayList<PlayerDTO>();
        players.add(PlayerDTO.builder().name("test database").type("expert").build());
        players.add(PlayerDTO.builder().name("test NOVICE").type("NOVICE").build());
        players.add(PlayerDTO.builder().name("test something").type("123").build());

        var requestBody = mapper.writeValueAsString(PlayerRequestDTO.builder().players(players).build());

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(API_ROUTE_BASE.concat("/players"))
                .contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody);

        when(playerService.processPlayers(any()))
                .thenReturn(players.stream().map(p -> p.toString()).collect(Collectors.toList()));

        mockMvc.perform(requestBuilder).andExpect(status().isOk()).andExpect(jsonPath("$.result", hasSize(3)));
    }



    @Test
    public void whenSendPlayersAndThrowExceptionShouldReturn500() throws Exception {

        var mapper = new ObjectMapper();

        var requestBody = mapper.writeValueAsString(PlayerRequestDTO.builder().players(null).build());

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(API_ROUTE_BASE.concat("/players"))
                .contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody);

        when(playerService.processPlayers(any()))
                .thenThrow(new RuntimeException());

        mockMvc.perform(requestBuilder).andExpect(status().isInternalServerError());
    }
}
