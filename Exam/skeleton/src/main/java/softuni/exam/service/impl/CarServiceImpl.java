package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.car.CarImportDto;
import softuni.exam.models.dto.car.CarWrapperImportDto;
import softuni.exam.models.entity.Car;
import softuni.exam.repository.CarRepository;
import softuni.exam.service.CarService;
import softuni.exam.util.ValidationUtils;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static softuni.exam.constants.Messages.INVALID_CAR;
import static softuni.exam.constants.Messages.SUCCESSFULLY_IMPORTED_CAR_FORMAT;
import static softuni.exam.constants.Paths.CAR_XML_PATH;

@Service
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final ValidationUtils validationUtils;
    private final XmlParser xmlParser;
    private final ModelMapper modelMapper;

    @Autowired
    public CarServiceImpl(CarRepository carRepository, ValidationUtils validationUtils, XmlParser xmlParser, ModelMapper modelMapper) {
        this.carRepository = carRepository;
        this.validationUtils = validationUtils;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean areImported() {
        return this.carRepository.count() > 0;
    }

    @Override
    public String readCarsFromFile() throws IOException {
        return Files.readString(CAR_XML_PATH);
    }

    @Override
    public String importCars() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();

        File file = CAR_XML_PATH.toFile();
        CarWrapperImportDto carWrapperImportDto = xmlParser.fromFile(file, CarWrapperImportDto.class);

        List<CarImportDto> carImportDtos = carWrapperImportDto.getCars();

        for (CarImportDto carDto : carImportDtos) {
            boolean isValid = validationUtils.isValid(carDto);

            if (this.carRepository.findFirstByPlateNumber(carDto.getPlateNumber()).isPresent()) {
                isValid = false;
            }

            if (isValid) {
                Car car = modelMapper.map(carDto, Car.class);
                this.carRepository.saveAndFlush(car);

                sb.append(String.format(SUCCESSFULLY_IMPORTED_CAR_FORMAT, car.getCarMake(), car.getCarModel()))
                        .append(System.lineSeparator());
            } else {
                sb.append(INVALID_CAR).append(System.lineSeparator());
            }

        }

        return sb.toString();
    }
}
