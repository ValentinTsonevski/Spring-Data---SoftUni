package softuni.exam.constants;

import java.nio.file.Path;

public enum Paths {
  ;
    public static final String COUNTRIES_JSON_PATH = "src/main/resources/files/json/countries.json";
    public static final String CITIES_JSON_PATH = "src/main/resources/files/json/cities.json";

    public static final Path FORECASTS_XML_PATH = Path.of("src/main/resources/files/xml/forecasts.xml");
}
