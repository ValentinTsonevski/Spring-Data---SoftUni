package com.example.football.repository;

import com.example.football.enums.Position;
import com.example.football.models.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Repository
public interface PlayerRepository extends JpaRepository<Player, Integer> {

    Optional<Player> findFirstByEmail(String email);
    Optional<List<Player>> findAllByBirthDateAfterAndBirthDateBeforeOrderByStatShootingDescStatPassingDescStatEnduranceDescLastName
            (LocalDate after,LocalDate before);
}
