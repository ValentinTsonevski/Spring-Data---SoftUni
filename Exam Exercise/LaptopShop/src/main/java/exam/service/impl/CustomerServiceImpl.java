package exam.service.impl;

import com.google.gson.Gson;
import exam.model.dto.customer.CustomerImportDto;
import exam.model.entity.Customer;
import exam.model.entity.Town;
import exam.repository.CustomerRepository;
import exam.repository.TownRepository;
import exam.service.CustomerService;
import exam.util.ValidationUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static exam.constants.Messages.INVALID_CUSTOMER;
import static exam.constants.Messages.SUCCESSFULLY_IMPORTED_CUSTOMER_FORMAT;
import static exam.constants.Paths.CUSTOMER_JASON_PATH;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final TownRepository townRepository;
    private final ValidationUtils validationUtils;
    private final ModelMapper modelMapper;
    private final Gson gson;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, TownRepository townRepository, ValidationUtils validationUtils, ModelMapper modelMapper, Gson gson) {
        this.customerRepository = customerRepository;
        this.townRepository = townRepository;
        this.validationUtils = validationUtils;
        this.modelMapper = modelMapper;
        this.gson = gson;
    }

    @Override
    public boolean areImported() {
        return this.customerRepository.count() > 0;
    }

    @Override
    public String readCustomersFileContent() throws IOException {
        return Files.readString(CUSTOMER_JASON_PATH);
    }

    @Override
    public String importCustomers() throws IOException {
        StringBuilder sb = new StringBuilder();
        CustomerImportDto[] customerImportDtos = gson.fromJson(readCustomersFileContent(), CustomerImportDto[].class);

        for (CustomerImportDto customerDto : customerImportDtos) {
            boolean isValid = validationUtils.isValid(customerDto);

            if(this.customerRepository.findFirstByEmail(customerDto.getEmail()).isPresent()){
               isValid = false;
            }

            if(isValid){
                Customer customer = modelMapper.map(customerDto, Customer.class);
                Town townToSet = this.townRepository.findFirstByName(customer.getTown().getName()).get();
                customer.setRegisteredOn(LocalDate.parse(customerDto.getRegisteredOn(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));

                customer.setTown(townToSet);

                this.customerRepository.saveAndFlush(customer);

                sb.append(String.format(SUCCESSFULLY_IMPORTED_CUSTOMER_FORMAT
                                ,customer.getFirstName()
                                ,customer.getLastName()
                                ,customer.getEmail()))
                        .append(System.lineSeparator());
            }else{
                sb.append(INVALID_CUSTOMER).append(System.lineSeparator());
            }

        }

        return sb.toString();
    }
}
