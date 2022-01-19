package ee.icd0004.weatherapp.mapper;

import ee.icd0004.weatherapp.model.MainDetails;
import ee.icd0004.weatherapp.weatherapi.CurrentWeatherResponse;

import java.util.Objects;

public class MainDetailsMapper {

    public MainDetails mapDetails(CurrentWeatherResponse weatherResponse) {
        MainDetails details = new MainDetails();

        details.setCity(weatherResponse.getName());
        details.setCoordinates(getCoordinates(weatherResponse));
        details.setTemperatureUnit(getTemperatureUnit(weatherResponse));

        return details;
    }

    private String getTemperatureUnit(CurrentWeatherResponse weatherResponse) {
        if (Objects.equals(weatherResponse.getUnit(), "metric")) {
            return "Celsius";
        }
        return "unknown";
    }

    private String getCoordinates(CurrentWeatherResponse weatherResponse) {
        String lat = weatherResponse.getCoordinates() != null ? weatherResponse.getCoordinates().getLat() : "unknown";
        String lon = weatherResponse.getCoordinates() != null ? weatherResponse.getCoordinates().getLon() : "unknown";
        return String.format("%s,%s", lat, lon);
    }

}
