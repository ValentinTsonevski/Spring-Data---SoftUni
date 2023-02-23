package softuni.productsshop.constants;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public enum Utils {
    ;
    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    public static final ModelMapper MODEL_MAPPER = new ModelMapper();

    public static void writeJsonIntoFile(List<?> objects, Path filePath) throws IOException {

        final FileWriter fileWriter = new FileWriter(filePath.toFile());

        GSON.toJson(objects,fileWriter);
        fileWriter.flush();
        fileWriter.close();
    }

}
