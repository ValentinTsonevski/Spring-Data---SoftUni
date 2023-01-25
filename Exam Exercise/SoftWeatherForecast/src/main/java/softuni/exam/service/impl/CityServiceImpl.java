package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.CityImportDTO;
import softuni.exam.models.entity.City;
import softuni.exam.repository.CityRepository;
import softuni.exam.repository.CountryRepository;
import softuni.exam.service.CityService;
import softuni.exam.util.ValidationUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import static softuni.exam.constants.Messages.FAILED_TO_ADD_CITY;
import static softuni.exam.constants.Messages.SUCCESSFULLY_ADDED_CITY_FORMAT;
import static softuni.exam.constants.Paths.CITIES_JSON_PATH;

@Service
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;
    private final CountryRepository countryRepository;
    private final ModelMapper modelMapper = new ModelMapper();
    private final Gson gson = new Gson();
    private  final ValidationUtils validationUtils;

    @Autowired
    public CityServiceImpl(CityRepository cityRepository, CountryRepository countryRepository, ValidationUtils validationUtils) {
        this.cityRepository = cityRepository;
        this.countryRepository = countryRepository;
        this.validationUtils = validationUtils;
    }


    @Override
    public boolean areImported() {
        return cityRepository.count() > 0;
    }

    @Override
    public String readCitiesFileContent() throws IOException {
        return Files.readString(Path.of(CITIES_JSON_PATH));
    }

    @Override
    public String importCities() throws IOException {
        StringBuilder sb = new StringBuilder();

        List<CityImportDTO> CityImportDTOS = Arrays.stream(gson.fromJson(readCitiesFileContent(), CityImportDTO[].class)).toList();

        for (CityImportDTO cityDTO : CityImportDTOS) {
            boolean isValid = validationUtils.isValid(cityDTO);

            if(this.cityRepository.findFirstByCityName(cityDTO.getCityName()).isPresent()){
             continue;
            }

            if(isValid){
                sb.append(String.format(SUCCESSFULLY_ADDED_CITY_FORMAT,cityDTO.getCityName(),cityDTO.getPopulation()))
                        .append(System.lineSeparator());

                if(this.countryRepository.findById(cityDTO.getCountry()).isPresent()){
                    City city = modelMapper.map(cityDTO, City.class);
                    city.setCountry(this.countryRepository.findById(cityDTO.getCountry()).get());
                    cityRepository.saveAndFlush(city);
                }else{
                    sb.append("Error").append(System.lineSeparator());
                }

            }else{
            sb.append(FAILED_TO_ADD_CITY).append(System.lineSeparator());
            }

        }

        return sb.toString();
    }
}
