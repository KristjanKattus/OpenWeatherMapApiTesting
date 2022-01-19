package ee.icd0004.weatherapp.mapper;

import ee.icd0004.weatherapp.model.CurrentWeatherReport;
import ee.icd0004.weatherapp.weatherapi.CurrentWeatherResponse;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class CurrentWeatherReportMapperTests {

    @Test
    public void shouldMapDateFromWeatherResponse() {
        Instant instant = LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant();
        long timeInSeconds = instant.getEpochSecond();
        CurrentWeatherReportMapper weatherReportMapper = new CurrentWeatherReportMapper();
        CurrentWeatherResponse weatherResponse = new CurrentWeatherResponse();
        weatherResponse.setEpochDate(timeInSeconds);

        CurrentWeatherReport weatherReport = weatherReportMapper.mapCurrentWeather(weatherResponse);

        assertThat(weatherReport.getDate(), equalTo(LocalDate.now()));
    }

    @Test
    public void shouldMapMainWeatherDetailsFromWeatherResponse() {
        CurrentWeatherReportMapper weatherReportMapper = new CurrentWeatherReportMapper();
        CurrentWeatherResponse weatherResponse = new CurrentWeatherResponse();
        CurrentWeatherResponse.WeatherDetails weatherDetails = getWeatherDetails();
        weatherResponse.setWeatherDetails(weatherDetails);

        CurrentWeatherReport weatherReport = weatherReportMapper.mapCurrentWeather(weatherResponse);

        assertThat(weatherReport.getTemperature(), equalTo(weatherDetails.getTemperature()));
        assertThat(weatherReport.getHumidity(), equalTo(weatherDetails.getHumidity()));
        assertThat(weatherReport.getPressure(), equalTo(weatherDetails.getPressure()));
    }

    @Test
    public void shouldMapMainWeatherDetails_whenNoDetailsGiven() {
        CurrentWeatherReportMapper weatherReportMapper = new CurrentWeatherReportMapper();
        CurrentWeatherResponse weatherResponse = new CurrentWeatherResponse();

        CurrentWeatherReport weatherReport = weatherReportMapper.mapCurrentWeather(weatherResponse);

        assertThat(weatherReport.getTemperature(), equalTo(0));
        assertThat(weatherReport.getHumidity(), equalTo(0));
        assertThat(weatherReport.getPressure(), equalTo(0));
    }

    private CurrentWeatherResponse.WeatherDetails getWeatherDetails() {
        CurrentWeatherResponse.WeatherDetails weatherDetails = new CurrentWeatherResponse.WeatherDetails();
        weatherDetails.setTemperature(15);
        weatherDetails.setHumidity(80);
        weatherDetails.setPressure(980);

        return weatherDetails;
    }

}
