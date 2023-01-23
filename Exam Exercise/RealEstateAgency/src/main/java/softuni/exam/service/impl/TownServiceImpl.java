package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ImportTownDTO;
import softuni.exam.models.entity.Town;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.TownService;
import softuni.exam.util.ValidationUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

import static softuni.exam.constants.Messages.INVALID_TOWN;
import static softuni.exam.constants.Messages.SUCCESSFULLY_IMPORTED_TOWN_FORMAT;
import static softuni.exam.constants.Paths.TOWNS_JSON_PATH;


@Service
public class TownServiceImpl implements TownService {
    private final ValidationUtils validationUtils;
    private final TownRepository townRepository;
    private final Gson gson;
    private final ModelMapper modelMapper;

    @Autowired
    public TownServiceImpl(ValidationUtils validationUtils, TownRepository townRepository, Gson gson, ModelMapper modelMapper) {
        this.validationUtils = validationUtils;
        this.townRepository = townRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
    }


    @Override
    public boolean areImported() {
        return townRepository.count() > 0;
    }

    @Override
    public String readTownsFileContent() throws IOException {
        return Files.readString(TOWNS_JSON_PATH);
    }

    @Override
    public String importTowns() throws IOException {
        StringBuilder sb = new StringBuilder();

        List<ImportTownDTO> importTownDTOS = Arrays.stream(gson.fromJson(readTownsFileContent(), ImportTownDTO[].class)).toList();

        for (ImportTownDTO townDTO : importTownDTOS) {

         boolean isValid = validationUtils.isValid(townDTO);

         if(this.townRepository.getFirstByTownName(townDTO.getTownName()).isPresent()){
             isValid = false;
         }

        if(isValid){
            Town townToImport = modelMapper.map(townDTO,Town.class);
            this.townRepository.saveAndFlush(townToImport);

            sb.append(String.format(SUCCESSFULLY_IMPORTED_TOWN_FORMAT,townToImport.getTownName(),townToImport.getPopulation()))
                    .append(System.lineSeparator());
        }else{
            sb.append(INVALID_TOWN);
        }

        }

        return sb.toString();
    }
}
