package com.example.gamestore.domain.repositories;

import com.example.gamestore.domain.dtos.GameDTO;
import com.example.gamestore.domain.entities.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@Repository
public interface GameRepository extends JpaRepository<Game,Long> {

    Optional<Game> findFirstByTitle(String title);

    Optional<Game> findById(Long id);

    @Query("select g.title as title, g.price as price  from Game as g")
    Set<GameDTO> getAllGames();

}
