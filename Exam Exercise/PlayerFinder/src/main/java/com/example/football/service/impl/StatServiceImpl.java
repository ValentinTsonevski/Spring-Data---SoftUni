package com.example.football.service.impl;

import com.example.football.models.dto.StatImportDTO;
import com.example.football.models.dto.StatWrapperImportDTO;
import com.example.football.models.entity.Stat;
import com.example.football.repository.StatRepository;
import com.example.football.service.StatService;
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
import java.util.List;

import static com.example.football.constants.Messages.INVALID_STAT;
import static com.example.football.constants.Messages.SUCCESSFULLY_IMPORTED_STAT_FORMAT;
import static com.example.football.constants.Paths.STATS_XML_PATH;

@Service
public class StatServiceImpl implements StatService {

    private final StatRepository statRepository;
    private final ValidationUtils validationUtils;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;

    @Autowired
    public StatServiceImpl(StatRepository statRepository, ValidationUtils validationUtils, ModelMapper modelMapper, XmlParser xmlParser) {
        this.statRepository = statRepository;
        this.validationUtils = validationUtils;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
    }


    @Override
    public boolean areImported() {
        return this.statRepository.count() > 0;
    }

    @Override
    public String readStatsFileContent() throws IOException {
        return Files.readString(STATS_XML_PATH);
    }

    @Override
    public String importStats() throws JAXBException, FileNotFoundException {
        StringBuilder sb = new StringBuilder();

        File file = STATS_XML_PATH.toFile();

        StatWrapperImportDTO statWrapperImportDTO = xmlParser.fromFile(file, StatWrapperImportDTO.class);

        List<StatImportDTO> statImportDTOS = statWrapperImportDTO.getStat().stream().toList();


        for (StatImportDTO statDTO : statImportDTOS) {
            boolean isValid = validationUtils.isValid(statDTO);

            if(this.statRepository.findFirstByPassingAndShootingAndEndurance(statDTO.getPassing(),
                    statDTO.getShooting(),statDTO.getEndurance()).isPresent()){
                isValid = false;
            }
            if(isValid){
                Stat stat = modelMapper.map(statDTO, Stat.class);
                this.statRepository.saveAndFlush(stat);
             sb.append(String.format(SUCCESSFULLY_IMPORTED_STAT_FORMAT,stat.getShooting(),stat.getPassing(),stat.getEndurance()))
                     .append(System.lineSeparator());
            }else{
                sb.append(INVALID_STAT).append(System.lineSeparator());
            }

        }

        return sb.toString();
    }
}
