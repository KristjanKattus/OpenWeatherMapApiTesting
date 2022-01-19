package ee.icd0004.weatherapp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import ee.icd0004.weatherapp.exception.CityNotFoundException;
import ee.icd0004.weatherapp.fileparser.FileParser;
import ee.icd0004.weatherapp.model.WeatherReport;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class WeatherAppFileTests {

    @Test
    public void testAllCities_whenParsedFromFile() throws IOException {
        FileParser parser = new FileParser();
        List<String> cities = parser.readCitiesFromFile();
        WeatherApp weatherApp = new WeatherApp();
        weatherApp.writeWeatherReportsToFiles();

        for (String city : cities) {
            WatchService watchService = FileSystems.getDefault().newWatchService();
            Path path = Paths.get("src/main/resources");
            path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_MODIFY);

            WatchKey key;
            while ((key = watchService.poll()) != null){
                try {
                    weatherApp.getWeatherReport(city);
                } catch (CityNotFoundException ignored){
                    responseDirectoryShouldNotHaveFailWithIncorrectCityName_whenParsedFromDataFile(city);
                    continue;
                }

                responseDirectoryShouldHaveFailWithCityName_whenParsedFromDataFile(city);
                fileShouldContainJson_whenParsedFromDataFile(city);
                key.reset();
            }
        }
    }

    @Test
    public void responseDirectoryShouldNotBeEmpty_whenParsedFromDataFile() throws IOException {
        File directory = new ClassPathResource("/response").getFile();
        assertThat(directory.list(), notNullValue());
    }


    public void responseDirectoryShouldHaveFailWithCityName_whenParsedFromDataFile(String city ) throws IOException {
        Resource file = new ClassPathResource("/response/" + city + "-weather.json");
        assertThat(file, notNullValue());
    }


    public void fileShouldContainJson_whenParsedFromDataFile(String city) throws IOException {
        ObjectMapper MAPPER = new ObjectMapper().registerModule(new JavaTimeModule());
        Resource file = new ClassPathResource("/response/" + city + "-weather.json");
        assertThat(MAPPER.readValue(file.getInputStream(), WeatherReport.class), notNullValue());
    }


    public void responseDirectoryShouldNotHaveFailWithIncorrectCityName_whenParsedFromDataFile(String city)  {
        assertThat(new ClassPathResource("/response/" + city + "-weather.json").isFile(), equalTo(false));
    }
}
