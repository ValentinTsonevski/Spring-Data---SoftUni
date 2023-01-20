package exam.service.impl;

import com.google.gson.Gson;
import exam.model.dto.shop.ShopImportDto;
import exam.model.dto.shop.ShopWrapperImportDto;
import exam.model.entity.Shop;
import exam.model.entity.Town;
import exam.repository.ShopRepository;
import exam.repository.TownRepository;
import exam.service.ShopService;
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

import static exam.constants.Messages.INVALID_SHOP;
import static exam.constants.Messages.SUCCESSFULLY_IMPORTED_SHOP_FORMAT;
import static exam.constants.Paths.SHOP_XML_PATH;

@Service
public class ShopServiceImpl implements ShopService {

    private final ShopRepository shopRepository;
    private final TownRepository townRepository;
    private final ValidationUtils validationUtils;
    private final XmlParser xmlParser;
    private final ModelMapper modelMapper;
    private final Gson gson;

    @Autowired
    public ShopServiceImpl(ShopRepository shopRepository, TownRepository townRepository, ValidationUtils validationUtils, XmlParser xmlParser, ModelMapper modelMapper, Gson gson) {
        this.shopRepository = shopRepository;
        this.townRepository = townRepository;
        this.validationUtils = validationUtils;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
        this.gson = gson;
    }

    @Override
    public boolean areImported() {
        return this.shopRepository.count() > 0;
    }

    @Override
    public String readShopsFileContent() throws IOException {
        return Files.readString(SHOP_XML_PATH);
    }

    @Override
    public String importShops() throws JAXBException, FileNotFoundException {
        StringBuilder sb = new StringBuilder();

        File file = SHOP_XML_PATH.toFile();

        ShopWrapperImportDto shopWrapperImportDto = xmlParser.fromFile(file, ShopWrapperImportDto.class);

        List<ShopImportDto> shopsDto = shopWrapperImportDto.getShops();

        for (ShopImportDto shopDto : shopsDto) {
            boolean isValid = validationUtils.isValid(shopDto);

            if(this.shopRepository.findFirstByName(shopDto.getName()).isPresent()){
                isValid = false;
            }

            if(isValid){
                Shop shop = modelMapper.map(shopDto, Shop.class);
                Town townToSet = this.townRepository.findFirstByName(shop.getTown().getName()).get();
                shop.setTown(townToSet);
                this.shopRepository.saveAndFlush(shop);

                sb.append(String.format(SUCCESSFULLY_IMPORTED_SHOP_FORMAT,shop.getName(),shop.getIncome()))
                        .append(System.lineSeparator());
            }else{
                sb.append(INVALID_SHOP).append(System.lineSeparator());
            }

        }

        return sb.toString();
    }
}
