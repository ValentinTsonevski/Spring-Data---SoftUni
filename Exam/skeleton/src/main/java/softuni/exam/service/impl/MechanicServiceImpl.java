package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.Mechanic.MechanicImportDto;
import softuni.exam.models.entity.Mechanic;
import softuni.exam.repository.MechanicRepository;
import softuni.exam.service.MechanicService;
import softuni.exam.util.ValidationUtils;

import java.io.IOException;
import java.nio.file.Files;

import static softuni.exam.constants.Messages.INVALID_MECHANIC;
import static softuni.exam.constants.Messages.SUCCESSFULLY_IMPORTED_MECHANIC_FORMAT;
import static softuni.exam.constants.Paths.MECHANIC_JSON_PATH;

@Service
public class MechanicServiceImpl implements MechanicService {

    private final MechanicRepository mechanicRepository;
    private final ValidationUtils validationUtils;
    private final ModelMapper modelMapper;
    private final Gson gson;

    @Autowired
    public MechanicServiceImpl(MechanicRepository mechanicRepository, ValidationUtils validationUtils,
                               ModelMapper modelMapper, Gson gson) {
        this.mechanicRepository = mechanicRepository;
        this.validationUtils = validationUtils;
        this.modelMapper = modelMapper;
        this.gson = gson;
    }

    @Override
    public boolean areImported() {
        return this.mechanicRepository.count() > 0;
    }

    @Override
    public String readMechanicsFromFile() throws IOException {
        return Files.readString(MECHANIC_JSON_PATH);
    }

    @Override
    public String importMechanics() throws IOException {
        StringBuilder sb = new StringBuilder();

        MechanicImportDto[] mechanicImportDtos = gson.fromJson(readMechanicsFromFile(), MechanicImportDto[].class);

        for (MechanicImportDto mechanicDto : mechanicImportDtos) {
            boolean isValid = validationUtils.isValid(mechanicDto);

            if (this.mechanicRepository.findFirstByEmail(mechanicDto.getEmail()).isPresent()) {
                isValid = false;
            }

            if (isValid) {
                Mechanic mechanic = modelMapper.map(mechanicDto, Mechanic.class);
                this.mechanicRepository.saveAndFlush(mechanic);

                sb.append(String.format(SUCCESSFULLY_IMPORTED_MECHANIC_FORMAT,mechanic.getFirstName(),mechanic.getLastName()))
                        .append(System.lineSeparator());
            } else {
                sb.append(INVALID_MECHANIC).append(System.lineSeparator());
            }

        }

        return sb.toString();
    }
}
