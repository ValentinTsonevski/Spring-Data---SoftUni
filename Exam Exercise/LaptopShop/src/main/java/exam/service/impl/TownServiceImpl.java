package exam.service.impl;

import com.google.gson.Gson;
import exam.model.dto.town.TownImportDto;
import exam.model.dto.town.TownWrapperImportDto;
import exam.model.entity.Town;
import exam.repository.TownRepository;
import exam.service.TownService;
import exam.util.ValidationUtils;
import exam.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static exam.constants.Messages.INVALID_TOWN;
import static exam.constants.Messages.SUCCESSFULLY_IMPORTED_TOWN_FORMAT;
import static exam.constants.Paths.TOWN_XML_PATH;

@Service
public class TownServiceImpl implements TownService {

    private final TownRepository townRepository;
    private final ValidationUtils validationUtils;
    private final XmlParser xmlParser;
    private final ModelMapper modelMapper;
    private final Gson gson;

    @Autowired
    public TownServiceImpl(TownRepository townRepository, ValidationUtils validationUtils, XmlParser xmlParser, ModelMapper modelMapper, Gson gson) {
        this.townRepository = townRepository;
        this.validationUtils = validationUtils;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
        this.gson = gson;
    }

    @Override
    public boolean areImported() {
        return this.townRepository.count() > 0;
    }

    @Override
    public String readTownsFileContent() throws IOException {
        return Files.readString(TOWN_XML_PATH);
    }

    @Override
    public String importTowns() throws JAXBException, FileNotFoundException {
        StringBuilder sb = new StringBuilder();

        File file = TOWN_XML_PATH.toFile();

        TownWrapperImportDto townWrapperImportDto = xmlParser.fromFile(file, TownWrapperImportDto.class);

        List<TownImportDto> townsDto = townWrapperImportDto.getTowns();

        for (TownImportDto town : townsDto) {
            boolean isValid = validationUtils.isValid(town);

            if(this.townRepository.findFirstByName(town.getName()).isPresent()){
                isValid = false;
            }

            if(isValid){
                Town townToSave = modelMapper.map(town, Town.class);
                this.townRepository.saveAndFlush(townToSave);

                sb.append(String.format(SUCCESSFULLY_IMPORTED_TOWN_FORMAT,townToSave.getName()))
                        .append(System.lineSeparator());
            }else{
                sb.append(INVALID_TOWN).append(System.lineSeparator());
            }

        }
        return sb.toString();
    }
}
