package br.com.example.demo.models;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "plr_player")
public class Player {

    @Id
    @Column(name = "plr_id")
    private UUID id;

    @Column(name = "plr_name", nullable = false)
    private String name;

    @Column(name = "plr_type", nullable = false)
    private String type;

}
