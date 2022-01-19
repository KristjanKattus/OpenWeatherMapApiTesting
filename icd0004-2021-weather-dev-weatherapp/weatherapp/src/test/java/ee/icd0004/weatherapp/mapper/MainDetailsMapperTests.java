package ee.icd0004.weatherapp.mapper;

import ee.icd0004.weatherapp.model.MainDetails;
import ee.icd0004.weatherapp.weatherapi.CurrentWeatherResponse;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class MainDetailsMapperTests {

    @Test
    public void shouldMapNameFromWeatherResponse() {
        MainDetailsMapper mainDetailsMapper = new MainDetailsMapper();
        CurrentWeatherResponse weatherResponse = new CurrentWeatherResponse();
        weatherResponse.setName("Testname");

        MainDetails details = mainDetailsMapper.mapDetails(weatherResponse);

        assertThat(details.getCity(), equalTo("Testname"));
    }

    @Test
    public void shouldMapCoordinatesFromWeatherResponse() {
        MainDetailsMapper mainDetailsMapper = new MainDetailsMapper();
        CurrentWeatherResponse weatherResponse = new CurrentWeatherResponse();
        CurrentWeatherResponse.Coordinates coordinates = new CurrentWeatherResponse.Coordinates();
        coordinates.setLat("11.11");
        coordinates.setLon("-12.12");
        weatherResponse.setCoordinates(coordinates);

        MainDetails details = mainDetailsMapper.mapDetails(weatherResponse);

        assertThat(details.getCoordinates(), equalTo("11.11,-12.12"));
    }

    @Test
    public void shouldMapTemperatureUnitFromWeatherResponse() {
        MainDetailsMapper mainDetailsMapper = new MainDetailsMapper();
        CurrentWeatherResponse weatherResponse = new CurrentWeatherResponse();
        weatherResponse.setUnit("metric");

        MainDetails details = mainDetailsMapper.mapDetails(weatherResponse);

        assertThat(details.getTemperatureUnit(), equalTo("Celsius"));
    }

    @Test
    public void shouldMapCoordinatesToUnknown_whenWeatherResponseCoordinatesAreNotExisting() {
        MainDetailsMapper mainDetailsMapper = new MainDetailsMapper();
        CurrentWeatherResponse weatherResponse = new CurrentWeatherResponse();

        MainDetails details = mainDetailsMapper.mapDetails(weatherResponse);

        assertThat(details.getCoordinates(), equalTo("unknown,unknown"));
    }

}
