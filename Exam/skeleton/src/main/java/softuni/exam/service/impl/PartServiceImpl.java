package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.part.PartImportDto;
import softuni.exam.models.entity.Part;
import softuni.exam.repository.PartRepository;
import softuni.exam.service.PartService;
import softuni.exam.util.ValidationUtils;

import java.io.IOException;
import java.nio.file.Files;

import static softuni.exam.constants.Messages.INVALID_PART;
import static softuni.exam.constants.Messages.SUCCESSFULLY_IMPORTED_PART_FORMAT;
import static softuni.exam.constants.Paths.PART_JSON_PATH;

@Service
public class PartServiceImpl implements PartService {

    private final PartRepository partRepository;
    private final ValidationUtils validationUtils;
    private final ModelMapper modelMapper;
    private final Gson gson;

    @Autowired
    public PartServiceImpl(PartRepository partRepository, ValidationUtils validationUtils, ModelMapper modelMapper, Gson gson) {
        this.partRepository = partRepository;
        this.validationUtils = validationUtils;
        this.modelMapper = modelMapper;
        this.gson = gson;
    }

    @Override
    public boolean areImported() {
        return this.partRepository.count() > 0;
    }

    @Override
    public String readPartsFileContent() throws IOException {
        return Files.readString(PART_JSON_PATH);
    }

    @Override
    public String importParts() throws IOException {
        StringBuilder sb = new StringBuilder();

        PartImportDto[] partImportDtos = gson.fromJson(readPartsFileContent(), PartImportDto[].class);

        for (PartImportDto partDto : partImportDtos) {
            boolean isValid = validationUtils.isValid(partDto);

            if(this.partRepository.findFirstByPartName(partDto.getPartName()).isPresent()){
             isValid = false;
            }

            if(isValid){
                Part part = modelMapper.map(partDto, Part.class);
                this.partRepository.saveAndFlush(part);

                sb.append(String.format(SUCCESSFULLY_IMPORTED_PART_FORMAT,part.getPartName(),part.getPrice()))
                        .append(System.lineSeparator());
            }else{
                sb.append(INVALID_PART).append(System.lineSeparator());
            }

        }

        return sb.toString();
    }
}
