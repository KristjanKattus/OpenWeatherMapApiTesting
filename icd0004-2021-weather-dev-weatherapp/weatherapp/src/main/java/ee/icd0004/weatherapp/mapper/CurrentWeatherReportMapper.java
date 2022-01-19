package ee.icd0004.weatherapp.mapper;

import ee.icd0004.weatherapp.model.CurrentWeatherReport;
import ee.icd0004.weatherapp.weatherapi.CurrentWeatherResponse;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

public class CurrentWeatherReportMapper {

    public CurrentWeatherReport mapCurrentWeather(CurrentWeatherResponse weatherResponse) {
        CurrentWeatherReport weatherReport = new CurrentWeatherReport();

        weatherReport.setDate(getDate(weatherResponse));
        weatherReport.setTemperature(getTemperature(weatherResponse));
        weatherReport.setHumidity(getHumidity(weatherResponse));
        weatherReport.setPressure(getPressure(weatherResponse));

        return weatherReport;
    }

    private int getTemperature(CurrentWeatherResponse weatherResponse) {
        return weatherResponse.getWeatherDetails() != null ? weatherResponse.getWeatherDetails().getTemperature() : 0;
    }

    private int getHumidity(CurrentWeatherResponse weatherResponse) {
        return weatherResponse.getWeatherDetails() != null ? weatherResponse.getWeatherDetails().getHumidity() : 0;
    }

    private int getPressure(CurrentWeatherResponse weatherResponse) {
        return weatherResponse.getWeatherDetails() != null ? weatherResponse.getWeatherDetails().getPressure() : 0;
    }

    private LocalDate getDate(CurrentWeatherResponse weatherResponse) {
        if (weatherResponse.getEpochDate() == null) {
            return null;
        }
        return Instant.ofEpochSecond(weatherResponse.getEpochDate()).atZone(ZoneId.systemDefault()).toLocalDate();
    }

}
