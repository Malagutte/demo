package br.com.example.demo.services;

import java.util.List;

import br.com.example.demo.dtos.PlayerDTO;

public interface IPlayerService {

    List<String> processPlayers(List<PlayerDTO> players);
}
