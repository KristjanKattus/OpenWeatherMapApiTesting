package ee.icd0004.weatherapp;

import ee.icd0004.weatherapp.exception.CityNotFoundException;
import ee.icd0004.weatherapp.model.WeatherReport;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class WeatherAppTests {

    private final String cityTallinn = "Tallinn";

    @Test
    public void shouldReturnMainDetailsInWeatherReport_whenCorrectCityNameIsGiven() {
        WeatherApp weatherApp = new WeatherApp();

        WeatherReport weatherReport = weatherApp.getWeatherReport(cityTallinn);

        assertThat(weatherReport.getMainDetails(), notNullValue());
    }

    @Test
    public void shouldReturnCoordinatesAddedTogetherInWeatherReport() {
        WeatherApp weatherApp = new WeatherApp();
        String coordinates = "59.437,24.7535";

        WeatherReport weatherReport = weatherApp.getWeatherReport(cityTallinn);

        assertThat(weatherReport.getMainDetails().getCoordinates(), notNullValue());
        assertThat(weatherReport.getMainDetails().getCoordinates(), equalTo(coordinates));
    }

    @Test
    public void shouldReturnTemperatureUnitInWeatherReport() {
        WeatherApp weatherApp = new WeatherApp();

        WeatherReport weatherReport = weatherApp.getWeatherReport(cityTallinn);

        assertThat(weatherReport.getMainDetails().getTemperatureUnit(), notNullValue());
        assertThat(weatherReport.getMainDetails().getTemperatureUnit(), equalTo("Celsius"));
    }

    @Test
    public void shouldReturnCityNameInWeatherReport() {
        WeatherApp weatherApp = new WeatherApp();

        WeatherReport weatherReport = weatherApp.getWeatherReport(cityTallinn);

        assertThat(weatherReport.getMainDetails().getCity(), notNullValue());
        assertThat(weatherReport.getMainDetails().getCity(), equalTo(cityTallinn));
    }

    @Test
    public void shouldThrowNotFoundException_whenInCorrectCityNameIsGiven(){
        WeatherApp weatherApp = new WeatherApp();
        String cityName = "T43Ut";
        String expectedErrorMessage = "Provided city name not found.";

        Exception exception = assertThrows(CityNotFoundException.class, () -> weatherApp.getWeatherReport(cityName));

        assertThat(exception.getMessage(), equalTo(expectedErrorMessage));
    }


}
