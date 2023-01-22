package com.example.football.service.impl;

import com.example.football.models.dto.TownImportDTO;
import com.example.football.models.entity.Town;
import com.example.football.repository.TownRepository;
import com.example.football.service.TownService;
import com.example.football.util.ValidationUtils;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

import static com.example.football.constants.Messages.INVALID_TOWN;
import static com.example.football.constants.Messages.SUCCESSFULLY_IMPORTED_TOWN_FORMAT;
import static com.example.football.constants.Paths.TOWNS_JSON_PATH;


@Service
public class TownServiceImpl implements TownService {
    private final TownRepository townRepository;
    private final ValidationUtils validationUtils;
    private final ModelMapper modelMapper;
    private final Gson gson;

    @Autowired
    public TownServiceImpl(TownRepository townRepository, ValidationUtils validationUtils, ModelMapper modelMapper, Gson gson) {
        this.townRepository = townRepository;
        this.validationUtils = validationUtils;
        this.modelMapper = modelMapper;
        this.gson = gson;
    }


    @Override
    public boolean areImported() {
        return this.townRepository.count() > 0;
    }

    @Override
    public String readTownsFileContent() throws IOException {
        return Files.readString(TOWNS_JSON_PATH);
    }

    @Override
    public String importTowns() throws IOException {
        StringBuilder sb = new StringBuilder();

        List<TownImportDTO> townImportDTOS = Arrays.stream(gson.fromJson(readTownsFileContent(), TownImportDTO[].class)).toList();

        for (TownImportDTO town : townImportDTOS) {
            boolean isValid = validationUtils.isValid(town);

            if (this.townRepository.findFirstByName(town.getName()).isPresent()) {
                isValid = false;
            }

            if (isValid) {
                Town townToSave = this.modelMapper.map(town, Town.class);
                this.townRepository.saveAndFlush(townToSave);

                sb.append(String.format(SUCCESSFULLY_IMPORTED_TOWN_FORMAT,townToSave.getName(),townToSave.getPopulation()))
                        .append(System.lineSeparator());
            } else {
                sb.append(INVALID_TOWN).append(System.lineSeparator());
            }
        }
        return sb.toString();
    }
}
