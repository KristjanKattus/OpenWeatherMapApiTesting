package ee.icd0004.weatherapp.fileparser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import ee.icd0004.weatherapp.model.CitiesFile;
import ee.icd0004.weatherapp.model.WeatherReport;
import lombok.extern.java.Log;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Objects;

@Log
public class FileParser {

    private static final String RESOURCES_DIRECTORY_NAME = "src/main/resources";
    private static final String REQUEST_DIRECTORY_NAME = "/request";
    private static final String FILE_NAME = REQUEST_DIRECTORY_NAME + "/data.json";
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public FileParser() {
        MAPPER.registerModule(new JavaTimeModule());
        MAPPER.setDateFormat((new SimpleDateFormat("yyyy-MM-dd")));
    }

    public List<String> readCitiesFromFile() throws IOException {
        File directory = new ClassPathResource(REQUEST_DIRECTORY_NAME).getFile();
        ClassPathResource resource = new ClassPathResource(FILE_NAME);

        if (Objects.requireNonNull(directory.list()).length < 1) {
            log.severe("The file data.json is not existing in: " + directory.getPath());
        }
        if (!resource.exists()) {
            log.severe("The file data.json is not in right format. Should be json.");
        }

        InputStream is = resource.getInputStream();
        CitiesFile citiesProvider = MAPPER.readValue(is, CitiesFile.class);
        return citiesProvider.getCities();
    }

    public void writeWeatherReportToFile(WeatherReport report) {
        FileWriter file = null;
        try {
            if (isResponseExisting(getFileNameToWrite(report))) {
                log.info(report.getMainDetails().getCity() + " weather report is being overwritten to updated one.");
            }
            file = new FileWriter(RESOURCES_DIRECTORY_NAME + getFileNameToWrite(report));
            file.write(MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(report));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (file != null) {
                    file.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isResponseExisting(String fileName) {
        ClassPathResource resource = new ClassPathResource(fileName);
        return resource.exists();
    }

    private String getFileNameToWrite(WeatherReport report) {
        return "/response/" + report.getMainDetails().getCity() + "-weather.json";
    }

}
