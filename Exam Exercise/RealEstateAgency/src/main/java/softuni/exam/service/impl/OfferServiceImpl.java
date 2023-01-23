package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.constants.ApartmentTypes;
import softuni.exam.models.dto.ImportOfferDTO;
import softuni.exam.models.dto.WrapperImportOffersDTO;
import softuni.exam.models.entity.Agent;
import softuni.exam.models.entity.Apartment;
import softuni.exam.models.entity.Offer;
import softuni.exam.repository.AgentRepository;
import softuni.exam.repository.ApartmentRepository;
import softuni.exam.repository.OfferRepository;
import softuni.exam.service.OfferService;
import softuni.exam.util.ValidationUtils;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import static softuni.exam.constants.Messages.*;
import static softuni.exam.constants.Paths.OFFERS_XML_PATH;

@Service
public class OfferServiceImpl implements OfferService {
    private final OfferRepository offerRepository;
    private final AgentRepository agentRepository;
    private final ApartmentRepository apartmentRepository;
    private final XmlParser xmlParser;
    private final ValidationUtils validationUtils;
    private final ModelMapper modelMapper;

    @Autowired
    public OfferServiceImpl(OfferRepository offerRepository, AgentRepository agentRepository
            , ApartmentRepository apartmentRepository, XmlParser xmlParser
            , ValidationUtils validationUtils, ModelMapper modelMapper) {
        this.offerRepository = offerRepository;
        this.agentRepository = agentRepository;
        this.apartmentRepository = apartmentRepository;
        this.xmlParser = xmlParser;
        this.validationUtils = validationUtils;
        this.modelMapper = modelMapper;
    }


    @Override
    public boolean areImported() {
        return this.offerRepository.count() > 0;
    }

    @Override
    public String readOffersFileContent() throws IOException {
        return Files.readString(OFFERS_XML_PATH);
    }

    @Override
    public String importOffers() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();

        File file = OFFERS_XML_PATH.toFile();

        WrapperImportOffersDTO wrapperImportOffersDTO = xmlParser.fromFile(file, WrapperImportOffersDTO.class);
        List<ImportOfferDTO> offers = wrapperImportOffersDTO.getOffers();

        for (ImportOfferDTO offer : offers) {
        boolean isValid = validationUtils.isValid(offer);

        if(agentRepository.getFirstByFirstName(offer.getAgent().getName()).isEmpty()){
            isValid = false;
        }
        if(apartmentRepository.findById(offer.getApartment().getId()).isEmpty()){
         isValid = false;
        }

        if(isValid){
            Apartment apartmentToSet = apartmentRepository.getById(offer.getApartment().getId());
            Agent agentToSet = agentRepository.getFirstByFirstName(offer.getAgent().getName()).get();
            Offer offerToSave = modelMapper.map(offer, Offer.class);

            offerToSave.setApartment(apartmentToSet);
            offerToSave.setAgent(agentToSet);
            offerToSave.setPublishedOn(LocalDate.parse(offer.getPublishedOn(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));

            this.offerRepository.saveAndFlush(offerToSave);

            sb.append(String.format(SUCCESSFULLY_IMPORTED_OFFER_FORMAT,offer.getPrice()))
                    .append(System.lineSeparator());
        }else{
            sb.append(INVALID_OFFER).append(System.lineSeparator());
        }

        }

        return sb.toString();
    }

    @Override
    public String exportOffers() {
        List<Offer> offers = this.offerRepository.findAllByApartment_ApartmentTypeIsOrderByApartmentAreaDescPriceAsc(ApartmentTypes.three_rooms)
                .orElseThrow(NoSuchElementException::new);
        return offers.stream().map(offer ->
            String.format(EXPORT_OFFER_FORMAT,
                    offer.getAgent().getFirstName(),
                    offer.getAgent().getLastName(),
                    offer.getId(),
                    offer.getApartment().getArea(),
                    offer.getApartment().getTown().getTownName(),
                    offer.getPrice())
        ).collect(Collectors.joining(System.lineSeparator()));
    }
}
