package br.com.example.demo.repositories;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.example.demo.models.Player;

@Repository
public interface IPlayerRepository extends CrudRepository<Player,UUID> {
    
}
