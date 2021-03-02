package br.com.example.demo.services.impl;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

import br.com.example.demo.dtos.PlayerDTO;
import br.com.example.demo.repositories.IPlayerRepository;

@ExtendWith(SpringExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
public class PlayerServiceImplTest {

    @MockBean
    private IPlayerRepository playerRepository;

    @MockBean
    private KafkaTemplate<String, String> kafkaTemplate;

    @InjectMocks
    private PlayerServiceImpl service;

    @BeforeAll
    public void setup(){
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(service, "playerTopic", "topic");
    }


    @Test
    public void whenPlayersIsNullShouldReturnEmptyList(){
        var result = service.processPlayers(null);

        assertTrue(result.isEmpty());
    }

    

    @Test
    public void whenPlayersIsNotNullShouldSaveAndSendToKafkaAndReturnList(){

        var players = new ArrayList<PlayerDTO>();
        players.add(PlayerDTO.builder().name("test database").type("expert").build());
        players.add(PlayerDTO.builder().name("test NOVICE").type("NOVICE").build());
        players.add(PlayerDTO.builder().name("test something").type("123").build());

        
        var result = service.processPlayers(players);

        assertFalse(result.isEmpty());
    }
}
