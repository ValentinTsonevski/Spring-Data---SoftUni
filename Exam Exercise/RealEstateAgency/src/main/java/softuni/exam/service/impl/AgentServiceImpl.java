package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ImportAgentDTO;
import softuni.exam.models.entity.Agent;
import softuni.exam.models.entity.Town;
import softuni.exam.repository.AgentRepository;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.AgentService;
import softuni.exam.util.ValidationUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

import static softuni.exam.constants.Messages.INVALID_AGENT;
import static softuni.exam.constants.Messages.SUCCESSFULLY_IMPORTED_AGENT_FORMAT;
import static softuni.exam.constants.Paths.AGENTS_JSON_PATH;

@Service
public class AgentServiceImpl implements AgentService {

    private final AgentRepository agentRepository;
    private final TownRepository townRepository;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidationUtils validationUtils;

    @Autowired
    public AgentServiceImpl(AgentRepository agentRepository, TownRepository townRepository, Gson gson, ModelMapper modelMapper, ValidationUtils validationUtils) {
        this.agentRepository = agentRepository;
        this.townRepository = townRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtils = validationUtils;
    }

    @Override
    public boolean areImported() {
        return this.agentRepository.count() > 0;
    }

    @Override
    public String readAgentsFromFile() throws IOException {
        return Files.readString(AGENTS_JSON_PATH);
    }

    @Override
    public String importAgents() throws IOException {
        StringBuilder sb = new StringBuilder();

        List<ImportAgentDTO> importAgentDTOS = Arrays.stream(gson.fromJson(readAgentsFromFile(), ImportAgentDTO[].class)).toList();

        for (ImportAgentDTO agentDTO : importAgentDTOS) {

            boolean isValid = validationUtils.isValid(agentDTO);

            if (this.agentRepository.getFirstByFirstName(agentDTO.getFirstName()).isPresent()) {
                isValid = false;
            }

            if (isValid) {
                if(townRepository.getFirstByTownName(agentDTO.getTown()).isPresent()){
                    Town townToSet = townRepository.getFirstByTownName(agentDTO.getTown()).get();
                    Agent agentToImport = modelMapper.map(agentDTO, Agent.class);
                    agentToImport.setTown(townToSet);

                   this.agentRepository.saveAndFlush(agentToImport);
                   sb.append(String.format(SUCCESSFULLY_IMPORTED_AGENT_FORMAT,agentToImport.getFirstName(),agentToImport.getLastName()))
                           .append(System.lineSeparator());
                }

            } else {
            sb.append(INVALID_AGENT);
            }

        }
        return sb.toString();
    }
}
