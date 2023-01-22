package com.example.football.service.impl;

import com.example.football.enums.Position;
import com.example.football.models.dto.PlayerImportDTO;
import com.example.football.models.dto.PlayerWrapperImportDTO;
import com.example.football.models.entity.Player;
import com.example.football.models.entity.Stat;
import com.example.football.models.entity.Team;
import com.example.football.models.entity.Town;
import com.example.football.repository.PlayerRepository;
import com.example.football.repository.StatRepository;
import com.example.football.repository.TeamRepository;
import com.example.football.repository.TownRepository;
import com.example.football.service.PlayerService;
import com.example.football.util.ValidationUtils;
import com.example.football.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static com.example.football.constants.Messages.*;
import static com.example.football.constants.Paths.PLAYER_XML_PATH;

@Service
public class PlayerServiceImpl implements PlayerService {
    private final PlayerRepository playerRepository;
    private final TownRepository townRepository;
    private final StatRepository statRepository;
    private final TeamRepository teamRepository;
    private final XmlParser xmlParser;
    private final ModelMapper modelMapper;
    private final ValidationUtils validationUtils;

    @Autowired
    public PlayerServiceImpl(PlayerRepository playerRepository, TownRepository townRepository, StatRepository statRepository
            , TeamRepository teamRepository, XmlParser xmlParser, ModelMapper modelMapper, ValidationUtils validationUtils) {
        this.playerRepository = playerRepository;
        this.townRepository = townRepository;
        this.statRepository = statRepository;
        this.teamRepository = teamRepository;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
        this.validationUtils = validationUtils;
    }


    @Override
    public boolean areImported() {
        return this.playerRepository.count() > 0;
    }

    @Override
    public String readPlayersFileContent() throws IOException {
        return Files.readString(PLAYER_XML_PATH);
    }

    @Override
    public String importPlayers() throws JAXBException, FileNotFoundException {
        StringBuilder sb = new StringBuilder();

        File file = PLAYER_XML_PATH.toFile();

        PlayerWrapperImportDTO playerWrapperImportDTO = xmlParser.fromFile(file, PlayerWrapperImportDTO.class);

        List<PlayerImportDTO> playerDTOS = playerWrapperImportDTO.getPlayer();

        for (PlayerImportDTO playerDTO : playerDTOS) {
            boolean isValid = validationUtils.isValid(playerDTO);

            if(this.playerRepository.findFirstByEmail(playerDTO.getEmail()).isPresent()){
                isValid = false;
            }

            if(isValid){

                if(this.townRepository.findFirstByName(playerDTO.getTown().getName()).isPresent()){
                    if(this.statRepository.findById(playerDTO.getStat().getId()).isPresent()){
                        if(this.teamRepository.findFirstByName(playerDTO.getTeam().getName()).isPresent()){

                            Player player = modelMapper.map(playerDTO, Player.class);
                           Town townToSet = this.townRepository.findFirstByName(playerDTO.getTown().getName()).get();
                            Stat statToSet = this.statRepository.findById(playerDTO.getStat().getId()).get();
                            Team teamToSet = this.teamRepository.findFirstByName(playerDTO.getTeam().getName()).get();

                            player.setBirthDate(LocalDate.parse(playerDTO.getBirthDate(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                            player.setTown(townToSet);
                            player.setStat(statToSet);
                            player.setTeam(teamToSet);

                            this.playerRepository.saveAndFlush(player);
                            sb.append(String.format(SUCCESSFULLY_IMPORTED_PLAYER_FORMAT,player.getFirstName(),player.getLastName()
                                            ,player.getPosition()))
                                    .append(System.lineSeparator());
                        }
                    }
                }

            }else{
                sb.append(INVALID_PLAYER).append(System.lineSeparator());
            }

        }

        return sb.toString();
    }

    @Override
    public String exportBestPlayers() {

        List<Player> players = this.playerRepository.findAllByBirthDateAfterAndBirthDateBeforeOrderByStatShootingDescStatPassingDescStatEnduranceDescLastName
                (LocalDate.of(1995, 1, 1), LocalDate.of(2003, 1, 1))
                .orElseThrow(NoSuchElementException::new).stream().toList();

        return players.stream().map(player -> String.format(DATA_EXPORT_FORMAT,player.getFirstName(),player.getLastName(),
                player.getPosition(), player.getTeam().getName(),player.getTeam().getStadiumName()))
                .collect(Collectors.joining(System.lineSeparator()));
    }
}
