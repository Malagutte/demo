package br.com.example.demo.dtos.request;

import java.util.List;

import br.com.example.demo.dtos.PlayerDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlayerRequestDTO {

    private List<PlayerDTO> players;
    
}
