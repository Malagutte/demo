package br.com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.example.demo.dtos.request.PlayerRequestDTO;
import br.com.example.demo.dtos.response.PlayerResponseDTO;
import br.com.example.demo.services.IPlayerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/v1")
@Api(tags = { "Player" })
public class PlayerController {

    @Autowired
    private IPlayerService playerService;

    @ApiResponses(value = { @ApiResponse(code = 200, message = "Ok", response = PlayerResponseDTO.class) })
    @PostMapping(path = "/players", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> processPlayers(@RequestBody PlayerRequestDTO request) {

        try {
            List<String> processPlayers = playerService.processPlayers(request.getPlayers());
            var response = PlayerResponseDTO.builder().result(processPlayers).build();
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }

    }

}
