package ee.icd0004.weatherapp.model;

import lombok.Data;

import java.util.List;

@Data
public class WeatherReport {
    private MainDetails mainDetails;
    private CurrentWeatherReport currentWeatherReport;
    public List<CurrentWeatherReport> forecastReport;
}
