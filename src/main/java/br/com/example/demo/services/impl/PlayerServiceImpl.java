package br.com.example.demo.services.impl;

import static br.com.example.demo.constants.Types.TYPE_EXPERT;
import static br.com.example.demo.constants.Types.TYPE_NOVICE;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import br.com.example.demo.dtos.PlayerDTO;
import br.com.example.demo.models.Player;
import br.com.example.demo.repositories.IPlayerRepository;
import br.com.example.demo.services.IPlayerService;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class PlayerServiceImpl implements IPlayerService {

    @Autowired
    private IPlayerRepository playerRepository;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Value("${application.topic.player.name}")
    private String playerTopic;

    @Override
    public List<String> processPlayers(List<PlayerDTO> players) {

        if (players == null || players.isEmpty()) {
            return new ArrayList<String>();
        }

        var novices = players.stream().filter(p -> StringUtils.equalsIgnoreCase(p.getType(), TYPE_NOVICE))
                .collect(Collectors.toList());

        var playerToSave = players.stream().filter(p -> StringUtils.equalsIgnoreCase(p.getType(), TYPE_EXPERT))
                .map(p -> Player.builder().id(UUID.randomUUID()).name(p.getName()).type(p.getType().toLowerCase()).build())
                .collect(Collectors.toList());

        sendToKafka(novices);
        playerRepository.saveAll(playerToSave);

        return players.stream().map(p -> p.toString()).collect(Collectors.toList());
    }

    private void sendToKafka(List<PlayerDTO> playersDTO) {

        var mapper = new ObjectMapper();
        playersDTO.forEach(p -> {

            try {
                var json = mapper.writeValueAsString(p);
                kafkaTemplate.send(playerTopic, json);
            } catch (Exception e) {
                log.error(e);
            }

        });

    }

}
