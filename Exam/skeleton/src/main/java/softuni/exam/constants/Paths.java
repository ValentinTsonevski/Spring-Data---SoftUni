package softuni.exam.constants;

import java.nio.file.Path;

public enum Paths {
    ;
    public static Path PART_JSON_PATH = Path.of("src/main/resources/files/json/parts.json");
    public static Path MECHANIC_JSON_PATH = Path.of("src/main/resources/files/json/mechanics.json");

    public static Path CAR_XML_PATH = Path.of("src/main/resources/files/xml/cars.xml");
    public static Path TASK_XML_PATH = Path.of("src/main/resources/files/xml/tasks.xml");
}
