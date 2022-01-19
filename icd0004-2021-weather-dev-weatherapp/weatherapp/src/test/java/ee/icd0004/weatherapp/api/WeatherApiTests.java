package ee.icd0004.weatherapp.api;

import ee.icd0004.weatherapp.exception.CityNotFoundException;
import ee.icd0004.weatherapp.weatherapi.CurrentWeatherResponse;
import ee.icd0004.weatherapp.weatherapi.ForecastResponse;
import ee.icd0004.weatherapp.weatherapi.WeatherApi;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class WeatherApiTests {

    private final String cityTallinn = "Tallinn";

    @Test
    public void shouldReturnCityNameInCurrentWeatherResponse_whenCorrectCityNameIsGiven() {
        WeatherApi weatherApi = new WeatherApi();

        CurrentWeatherResponse weatherResponse = weatherApi.getCurrentWeatherResponse(cityTallinn);

        assertThat(weatherResponse.getName(), equalTo(cityTallinn));
    }

    @Test
    public void shouldThrowNotFoundException_whenInCorrectCityNameIsGiven(){
        String cityName = "T43Ut";
        WeatherApi weatherApi = new WeatherApi();
        String expectedErrorMessage = "Provided city name not found.";

        Exception exception = assertThrows(CityNotFoundException.class, () -> weatherApi.getCurrentWeatherResponse(cityName));

        assertThat(exception.getMessage(), equalTo(expectedErrorMessage));
    }

    @Test
    public void shouldReturnCoordinatesInCurrentWeatherResponse() {
        String lat = "59.437";
        String lon = "24.7535";
        WeatherApi weatherApi = new WeatherApi();

        CurrentWeatherResponse weatherResponse = weatherApi.getCurrentWeatherResponse(cityTallinn);

        assertThat(weatherResponse.getCoordinates(), notNullValue());
        assertThat(weatherResponse.getCoordinates().getLat(), equalTo(lat));
        assertThat(weatherResponse.getCoordinates().getLon(), equalTo(lon));
    }

    @Test
    public void shouldReturnMainWeatherDetailsInCurrentWeatherResponse() {
        WeatherApi weatherApi = new WeatherApi();

        CurrentWeatherResponse weatherResponse = weatherApi.getCurrentWeatherResponse(cityTallinn);

        assertThat(weatherResponse.getWeatherDetails(), notNullValue());
    }

    @Test
    public void shouldContainMainWeatherDetailsInCurrentWeatherResponse() {
        WeatherApi weatherApi = new WeatherApi();

        CurrentWeatherResponse weatherResponse = weatherApi.getCurrentWeatherResponse(cityTallinn);

        assertThat(weatherResponse.getEpochDate(), notNullValue());
        assertThat(weatherResponse.getWeatherDetails().getHumidity(), notNullValue());
        assertThat(weatherResponse.getWeatherDetails().getTemperature(), notNullValue());
        assertThat(weatherResponse.getWeatherDetails().getPressure(), notNullValue());
    }

    @Test
    public void shouldContainThreeDayForecastInForecastWeatherResponse() {
        WeatherApi weatherApi = new WeatherApi();

        ForecastResponse forecastResponse = weatherApi.getForecastResponse(cityTallinn);
        assertThat(forecastResponse.getWeatherResponse().size(), equalTo(40));
        for (var threeHourForecast: forecastResponse.getWeatherResponse()) {
            assertThat(threeHourForecast.getEpochDate(), notNullValue());
            assertThat(threeHourForecast.getWeatherDetails().getHumidity(), notNullValue());
            assertThat(threeHourForecast.getWeatherDetails().getPressure(), notNullValue());
            assertThat(threeHourForecast.getWeatherDetails().getTemperature(), notNullValue());
        }
    }

    @Test
    public void shouldContainCityNameInForecast_WhenCorrectCityNameIsGiven() {
        WeatherApi weatherApi = new WeatherApi();

        ForecastResponse forecastResponse = weatherApi.getForecastResponse(cityTallinn);
        assertThat(forecastResponse.getCity().getName(), equalTo(cityTallinn));
    }

    @Test
    public void shouldReturnCoordinatesInForecastResponse() {
        String lat = "59.437";
        String lon = "24.7535";
        WeatherApi weatherApi = new WeatherApi();

        ForecastResponse forecastResponse = weatherApi.getForecastResponse(cityTallinn);

        assertThat(forecastResponse.getCity().getCoordinates(), notNullValue());
        assertThat(forecastResponse.getCity().getCoordinates().getLat(), equalTo(lat));
        assertThat(forecastResponse.getCity().getCoordinates().getLon(), equalTo(lon));
    }
}
