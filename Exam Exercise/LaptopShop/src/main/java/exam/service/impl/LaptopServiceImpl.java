package exam.service.impl;

import com.google.gson.Gson;
import exam.model.dto.laptop.LaptopImportDto;
import exam.model.entity.Laptop;
import exam.model.entity.Shop;
import exam.repository.LaptopRepository;
import exam.repository.ShopRepository;
import exam.service.LaptopService;
import exam.util.ValidationUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;

import static exam.constants.Messages.INVALID_LAPTOP;
import static exam.constants.Messages.SUCCESSFULLY_IMPORTED_LAPTOP_FORMAT;
import static exam.constants.Paths.LAPTOP_JASON_PATH;

@Service
public class LaptopServiceImpl implements LaptopService {

    private final LaptopRepository laptopRepository;
    private final ShopRepository shopRepository;
    private final ValidationUtils validationUtils;
    private final ModelMapper modelMapper;
    private final Gson gson;

    @Autowired
    public LaptopServiceImpl(LaptopRepository laptopRepository, ShopRepository shopRepository, ValidationUtils validationUtils, ModelMapper modelMapper, Gson gson) {
        this.laptopRepository = laptopRepository;
        this.shopRepository = shopRepository;
        this.validationUtils = validationUtils;
        this.modelMapper = modelMapper;
        this.gson = gson;
    }

    @Override
    public boolean areImported() {
        return this.laptopRepository.count() > 0;
    }

    @Override
    public String readLaptopsFileContent() throws IOException {
        return Files.readString(LAPTOP_JASON_PATH);
    }

    @Override
    public String importLaptops() throws IOException {
        StringBuilder sb = new StringBuilder();

        LaptopImportDto[] laptopImportDtos = gson.fromJson(readLaptopsFileContent(), LaptopImportDto[].class);

        for (LaptopImportDto laptopDto : laptopImportDtos) {
            boolean isValid = validationUtils.isValid(laptopDto);

            if(this.laptopRepository.getFirstByMacAddress(laptopDto.getMacAddress()).isPresent()){
             isValid = false;
            }

            if(isValid){
                Laptop laptop = modelMapper.map(laptopDto, Laptop.class);

                if(this.shopRepository.findFirstByName(laptop.getShop().getName()).isPresent()){

                    Shop shopToSet = this.shopRepository.findFirstByName(laptop.getShop().getName()).get();

                    laptop.setShop(shopToSet);

                    this.laptopRepository.saveAndFlush(laptop);

                    sb.append(String.format(SUCCESSFULLY_IMPORTED_LAPTOP_FORMAT,laptop.getMacAddress(),
                                    laptop.getCpuSpeed(),
                                    laptop.getRam(),
                                    laptop.getStorage()))
                            .append(System.lineSeparator());
                }

            }else{
                sb.append(INVALID_LAPTOP).append(System.lineSeparator());
            }
        }

        return sb.toString();
    }

    @Override
    public String exportBestLaptops() {
        return null;
    }
}
