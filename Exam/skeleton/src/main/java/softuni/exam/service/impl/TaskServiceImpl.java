package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.enums.CarType;
import softuni.exam.models.dto.task.TaskImportDto;
import softuni.exam.models.dto.task.TaskWrapperImportDto;
import softuni.exam.models.entity.Car;
import softuni.exam.models.entity.Mechanic;
import softuni.exam.models.entity.Part;
import softuni.exam.models.entity.Task;
import softuni.exam.repository.CarRepository;
import softuni.exam.repository.MechanicRepository;
import softuni.exam.repository.PartRepository;
import softuni.exam.repository.TaskRepository;
import softuni.exam.service.TaskService;
import softuni.exam.util.ValidationUtils;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static softuni.exam.constants.Messages.*;
import static softuni.exam.constants.Paths.TASK_XML_PATH;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final MechanicRepository mechanicRepository;
    private final CarRepository carRepository;
    private final PartRepository partRepository;
    private final ValidationUtils validationUtils;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, MechanicRepository mechanicRepository,
                           CarRepository carRepository, PartRepository partRepository,
                           ValidationUtils validationUtils, ModelMapper modelMapper, XmlParser xmlParser) {
        this.taskRepository = taskRepository;
        this.mechanicRepository = mechanicRepository;
        this.carRepository = carRepository;
        this.partRepository = partRepository;
        this.validationUtils = validationUtils;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
    }


    @Override
    public boolean areImported() {
        return taskRepository.count() > 0;
    }

    @Override
    public String readTasksFileContent() throws IOException {
        return Files.readString(TASK_XML_PATH);
    }

    @Override
    public String importTasks() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();
        File file = TASK_XML_PATH.toFile();

        TaskWrapperImportDto taskWrapperImportDto = xmlParser.fromFile(file, TaskWrapperImportDto.class);
        List<TaskImportDto> taskDtos = taskWrapperImportDto.getTasks();

        for (TaskImportDto taskDto : taskDtos) {
            boolean isValid = validationUtils.isValid(taskDto);

            if (this.mechanicRepository.findFirstByFirstName(taskDto.getMechanic().getFirstName()).isEmpty()) {
             isValid = false;
            }

            if(isValid){

                if(this.carRepository.findById(taskDto.getCar().getId()).isPresent()){
                    if(this.partRepository.findById(taskDto.getPart().getId()).isPresent()){

                        Mechanic mechanicToSet = this.mechanicRepository.findFirstByFirstName(taskDto.getMechanic().getFirstName()).get();
                        Car carToSet = this.carRepository.findById(taskDto.getCar().getId()).get();
                        Part partToSet = this.partRepository.findById(taskDto.getPart().getId()).get();

                        Task task = modelMapper.map(taskDto, Task.class);

                        task.setMechanic(mechanicToSet);
                        task.setCar(carToSet);
                        task.setPart(partToSet);
                        task.setDate(LocalDateTime.parse(taskDto.getDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

                        this.taskRepository.saveAndFlush(task);

                        sb.append(String.format(SUCCESSFULLY_IMPORTED_TASK_FORMAT,task.getPrice()))
                                .append(System.lineSeparator());
                    }
                }

            }else{
                sb.append(INVALID_TASK).append(System.lineSeparator());
            }

        }
        return sb.toString();
    }

    @Override
    public String getCoupeCarTasksOrderByPrice() {
        List<Task> tasks = this.taskRepository.findAllByCar_CarTypeOrderByPriceDesc(CarType.coupe)
                .orElseThrow(NoSuchElementException::new);

        return tasks.stream().map(task -> String.format(TASK_EXPORT_FORMAT,
                task.getCar().getCarMake(),
                task.getCar().getCarModel(),
                task.getCar().getKilometers(),
                task.getMechanic().getFirstName(),
                task.getMechanic().getLastName(),
                task.getId(),
                task.getCar().getEngine(),
                task.getPrice()))
                .collect(Collectors.joining(System.lineSeparator()));
    }
}
