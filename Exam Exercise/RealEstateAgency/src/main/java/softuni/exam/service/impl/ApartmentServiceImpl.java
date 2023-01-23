package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ImportApartmentDTO;
import softuni.exam.models.dto.WrapperImportApartmentDTO;
import softuni.exam.models.entity.Apartment;
import softuni.exam.models.entity.Town;
import softuni.exam.repository.ApartmentRepository;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.ApartmentService;
import softuni.exam.util.ValidationUtils;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static softuni.exam.constants.Messages.INVALID_APARTMENT;
import static softuni.exam.constants.Messages.SUCCESSFULLY_IMPORTED_APARTMENT_FORMAT;
import static softuni.exam.constants.Paths.APARTMENTS_XML_PATH;

@Service
public class ApartmentServiceImpl implements ApartmentService {
    private final ApartmentRepository apartmentRepository;
    private final TownRepository townRepository;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;
    private final ValidationUtils validationUtils;


    @Autowired
    public ApartmentServiceImpl(ApartmentRepository apartmentRepository, TownRepository townRepository, ModelMapper modelMapper, XmlParser xmlParser, ValidationUtils validationUtils) {
        this.apartmentRepository = apartmentRepository;
        this.townRepository = townRepository;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
        this.validationUtils = validationUtils;
    }


    @Override
    public boolean areImported() {
        return this.apartmentRepository.count() > 0;
    }

    @Override
    public String readApartmentsFromFile() throws IOException {
        return Files.readString(APARTMENTS_XML_PATH);
    }

    @Override
    public String importApartments() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();

        File file = APARTMENTS_XML_PATH.toFile();

        WrapperImportApartmentDTO wrapperApartmentDTO = xmlParser.fromFile(file, WrapperImportApartmentDTO.class);

        List<ImportApartmentDTO> apartments = wrapperApartmentDTO.getApartments();

        for (ImportApartmentDTO apartment : apartments) {
            boolean isValid = validationUtils.isValid(apartment);


            if (this.apartmentRepository.getApartmentByAreaAndTownTownName(apartment.getArea(),apartment.getTown()).isPresent()) {
                isValid = false;
            }

            if (isValid) {
                if(this.townRepository.getFirstByTownName(apartment.getTown()).isPresent()){

                    Apartment apartmentToSave = modelMapper.map(apartment, Apartment.class);
                    Town townToSet = this.townRepository.getFirstByTownName(apartment.getTown()).get();
                    apartmentToSave.setTown(townToSet);

                    this.apartmentRepository.saveAndFlush(apartmentToSave);

                    sb.append(String.format(SUCCESSFULLY_IMPORTED_APARTMENT_FORMAT,apartmentToSave.getApartmentType(),apartmentToSave.getArea()))
                            .append(System.lineSeparator());
                }
            } else {
                sb.append(INVALID_APARTMENT).append(System.lineSeparator());
            }


        }

        return sb.toString();
    }
}
