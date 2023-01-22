package com.example.football.service.impl;

import com.example.football.models.dto.TeamImportDTO;
import com.example.football.models.dto.TownImportDTO;
import com.example.football.models.entity.Team;
import com.example.football.models.entity.Town;
import com.example.football.repository.TeamRepository;
import com.example.football.repository.TownRepository;
import com.example.football.service.TeamService;
import com.example.football.util.ValidationUtils;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

import static com.example.football.constants.Messages.INVALID_TEAM;
import static com.example.football.constants.Messages.SUCCESSFULLY_IMPORTED_TEAM_FORMAT;
import static com.example.football.constants.Paths.TEAMS_JSON_PATH;

@Service
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;
    private final TownRepository townRepository;
    private final ValidationUtils validationUtils;
    private final ModelMapper modelMapper;
    private final Gson gson;

    @Autowired
    public TeamServiceImpl(TeamRepository teamRepository, TownRepository townRepository, ValidationUtils validationUtils, ModelMapper modelMapper, Gson gson) {
        this.teamRepository = teamRepository;
        this.townRepository = townRepository;
        this.validationUtils = validationUtils;
        this.modelMapper = modelMapper;
        this.gson = gson;
    }

    @Override
    public boolean areImported() {
        return this.teamRepository.count() > 0;
    }

    @Override
    public String readTeamsFileContent() throws IOException {
        return Files.readString(TEAMS_JSON_PATH);
    }

    @Override
    public String importTeams() throws IOException {
        StringBuilder sb = new StringBuilder();

        TeamImportDTO[] teamImportDTOS = gson.fromJson(readTeamsFileContent(), TeamImportDTO[].class);

        for (TeamImportDTO teamDTO : teamImportDTOS) {
            boolean isValid = validationUtils.isValid(teamDTO);

            if(this.teamRepository.findFirstByName(teamDTO.getName()).isPresent()){
                isValid = false;
            }

            if(isValid){
                if(this.townRepository.findFirstByName(teamDTO.getTownName()).isPresent()){
                    Team team = modelMapper.map(teamDTO, Team.class);
                    Town townToSet = townRepository.findFirstByName(team.getTown().getName()).get();
                    team.setTown(townToSet);
                    this.teamRepository.saveAndFlush(team);

                    sb.append(String.format(SUCCESSFULLY_IMPORTED_TEAM_FORMAT,team.getName(),team.getFanBase()))
                            .append(System.lineSeparator());
                }

            }else{
                sb.append(INVALID_TEAM).append(System.lineSeparator());
            }

        }


        return sb.toString();
    }
}
