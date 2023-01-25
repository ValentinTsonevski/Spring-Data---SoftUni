package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.DaysOfWeek;
import softuni.exam.models.dto.ForecastImportDTO;
import softuni.exam.models.dto.ForecastsWrapperDTO;
import softuni.exam.models.entity.City;
import softuni.exam.models.entity.Forecast;
import softuni.exam.repository.CityRepository;
import softuni.exam.repository.ForecastRepository;
import softuni.exam.service.ForecastService;
import softuni.exam.util.ValidationUtils;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

import static softuni.exam.constants.Messages.*;
import static softuni.exam.constants.Paths.FORECASTS_XML_PATH;

@Service
public class ForecastServiceImpl implements ForecastService {

    private final ForecastRepository forecastRepository;
    private final CityRepository cityRepository;

    private final Gson gson = new Gson();
    private final ModelMapper modelMapper = new ModelMapper();
    private final ValidationUtils validationUtils;
    private final XmlParser xmlParser;


    @Autowired
    public ForecastServiceImpl(ForecastRepository forecastRepository, CityRepository cityRepository, ValidationUtils validationUtils, XmlParser xmlParser) {
        this.forecastRepository = forecastRepository;
        this.cityRepository = cityRepository;
        this.validationUtils = validationUtils;
        this.xmlParser = xmlParser;
    }


    @Override
    public boolean areImported() {
        return this.forecastRepository.count() > 0;
    }

    @Override
    public String readForecastsFromFile() throws IOException {
        return Files.readString(FORECASTS_XML_PATH);
    }

    @Override
    public String importForecasts() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();
        File file = FORECASTS_XML_PATH.toFile();

        ForecastsWrapperDTO forecastsWrapperDTO = xmlParser.fromFile(file, ForecastsWrapperDTO.class);

        List<ForecastImportDTO> forecasts = forecastsWrapperDTO.getForecasts();

        for (ForecastImportDTO forecast : forecasts) {
            boolean isValid = validationUtils.isValid(forecast);

            if (isValid) {

                if (this.cityRepository.findById(forecast.getCity()).isPresent()) {

                    City city = this.cityRepository.findById(forecast.getCity()).get();
                    Forecast forecastToSave = modelMapper.map(forecast, Forecast.class);

                    forecastToSave.setCity(city);

                    forecastToSave.setSunrise(LocalTime.parse(forecast.getSunrise(),
                            DateTimeFormatter.ofPattern("HH:mm:ss")));
                    forecastToSave.setSunset(LocalTime.parse(forecast.getSunset(),
                            DateTimeFormatter.ofPattern("HH:mm:ss")));

                    this.forecastRepository.saveAndFlush(forecastToSave);

                    sb.append(String.format(SUCCESSFULLY_ADDED_FORECAST_FORMAT,
                                    forecastToSave.getDayOfWeek(), forecastToSave.getMaxTemperature()))
                            .append(System.lineSeparator());
                }
               continue;
            }
          sb.append(FAILED_TO_ADD_FORECAST).append(System.lineSeparator());
        }

        return sb.toString();
    }

    @Override
    public String exportForecasts() {
       final Set<Forecast> forecast = this.forecastRepository
               .findAllByDayOfWeekAndCity_PopulationLessThanOrderByMaxTemperatureDescIdAsc(DaysOfWeek.SUNDAY, 150000L)
                .orElseThrow(NoSuchElementException::new);


        return forecast.stream().map(forecast1 -> String.format(FORECAST_EXPORT_PRINT_FORMAT,forecast1.getCity().getCityName()
                ,forecast1.getMinTemperature()
                ,forecast1.getMaxTemperature()
                ,forecast1.getSunrise()
                ,forecast1.getSunset())).collect(Collectors.joining(System.lineSeparator()));
    }
}
