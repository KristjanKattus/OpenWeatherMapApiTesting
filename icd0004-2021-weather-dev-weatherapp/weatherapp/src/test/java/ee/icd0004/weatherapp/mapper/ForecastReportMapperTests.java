package ee.icd0004.weatherapp.mapper;

import ee.icd0004.weatherapp.exception.DateNotFoundException;
import ee.icd0004.weatherapp.model.CurrentWeatherReport;
import ee.icd0004.weatherapp.weatherapi.CurrentWeatherResponse;
import ee.icd0004.weatherapp.weatherapi.ForecastResponse;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ForecastReportMapperTests {

    @Test
    public void shouldMapDateForEachDayForecast_withCorrectOrder() {
        ForecastReportMapper forecastReportMapper = new ForecastReportMapper();
        ForecastResponse forecastResponse = new ForecastResponse();
        
        List<CurrentWeatherResponse> forecastDetails = getForecastDetails(false);
        forecastResponse.setWeatherResponse(forecastDetails);


        List<CurrentWeatherReport> currentWeatherResponses = forecastReportMapper.mapForecast(forecastResponse);
        for (int dayFromToday = 1; dayFromToday < 4; dayFromToday++) {
            assertThat(currentWeatherResponses.get(dayFromToday - 1).getDate(), equalTo(LocalDate.now().plusDays(dayFromToday)));
        }
    }

    @Test
    public void shouldNotContainTodaysForecast() {
        ForecastReportMapper forecastReportMapper = new ForecastReportMapper();
        ForecastResponse forecastResponse = new ForecastResponse();

        List<CurrentWeatherResponse> forecastDetails = getForecastDetails(false);
        forecastResponse.setWeatherResponse(forecastDetails);


        List<CurrentWeatherReport> currentWeatherResponses = forecastReportMapper.mapForecast(forecastResponse);

        assertThat(currentWeatherResponses.get(0).getDate(), not(LocalDate.now()));
    }

    @Test
    public void shouldCombineForecastIntoThreeDayForecastAndHaveWeatherDetails() {
        ForecastReportMapper forecastReportMapper = new ForecastReportMapper();
        ForecastResponse forecastResponse = new ForecastResponse();
        
        List<CurrentWeatherResponse> forecastDetails = getForecastDetails(false);
        forecastResponse.setWeatherResponse(forecastDetails);

        for (var dailyForecast : forecastReportMapper.mapForecast(forecastResponse)) {
            assertThat(dailyForecast.getTemperature(), notNullValue());
            assertThat(dailyForecast.getHumidity(), notNullValue());
            assertThat(dailyForecast.getPressure(), notNullValue());
        }
    }

    @Test
    public void shouldThrowException_WhenForecastReportHasNoDate() {
        ForecastReportMapper forecastReportMapper = new ForecastReportMapper();
        ForecastResponse forecastResponse = new ForecastResponse();
        List<CurrentWeatherResponse> currentWeatherResponses = new ArrayList<>();
        CurrentWeatherResponse currentWeatherResponse = new CurrentWeatherResponse();
        String noDateFoundExceptionMessage = "ForecastReport has no date.";

        currentWeatherResponses.add(currentWeatherResponse);
        forecastResponse.setWeatherResponse(currentWeatherResponses);


        Exception exception = assertThrows(DateNotFoundException.class, () -> forecastReportMapper.mapForecast(forecastResponse));

        assertThat(exception.getMessage(), equalTo(noDateFoundExceptionMessage));
    }

    @Test
    public void shouldMapForecastWeatherDetails_WhenNoDetailsGiven() {
        ForecastReportMapper forecastReportMapper = new ForecastReportMapper();
        ForecastResponse forecastResponse = new ForecastResponse();

        List<CurrentWeatherResponse> forecastDetails = getForecastDetails(true);
        forecastResponse.setWeatherResponse(forecastDetails);

        for (var dailyForecast : forecastReportMapper.mapForecast(forecastResponse)) {
            assertThat(dailyForecast.getTemperature(), equalTo(0));
            assertThat(dailyForecast.getHumidity(), equalTo(0));
            assertThat(dailyForecast.getPressure(), equalTo(0));
        }
    }

    private List<CurrentWeatherResponse> getForecastDetails(boolean emptyDetails) {
        List<CurrentWeatherResponse> weatherResponse = new ArrayList<>();

        for (int dayNumberFromToday = 0; dayNumberFromToday < 5; dayNumberFromToday++) {
            weatherResponse.add(getOneDayCurrentWeatherResponse(dayNumberFromToday, emptyDetails));
        }

        return weatherResponse;
    }

    public CurrentWeatherResponse getOneDayCurrentWeatherResponse (int dayNumberFromToday, boolean emptyDetails){
        CurrentWeatherResponse currentWeatherResponse = new CurrentWeatherResponse();
        CurrentWeatherResponse.WeatherDetails weatherDetails = new CurrentWeatherResponse.WeatherDetails();

        Instant instant = LocalDate.now().atStartOfDay(ZoneId.systemDefault()).plusDays(dayNumberFromToday).toInstant();
        long timeInSeconds = instant.getEpochSecond();

        for (int i = 0; i < 8; i++) {
            if (!emptyDetails){
                weatherDetails.setTemperature(15);
                weatherDetails.setHumidity(80);
                weatherDetails.setPressure(980);
            }
            currentWeatherResponse.setWeatherDetails(weatherDetails);
            currentWeatherResponse.setEpochDate(timeInSeconds);
        }
        return currentWeatherResponse;
    }
}
