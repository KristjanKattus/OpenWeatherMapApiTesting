package ee.icd0004.weatherapp.mapper;

import ee.icd0004.weatherapp.exception.DateNotFoundException;
import ee.icd0004.weatherapp.model.CurrentWeatherReport;
import ee.icd0004.weatherapp.weatherapi.CurrentWeatherResponse;
import ee.icd0004.weatherapp.weatherapi.ForecastResponse;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ForecastReportMapper {

    public List<CurrentWeatherReport> mapForecast (ForecastResponse forecastResponse) {
        CurrentWeatherReportMapper currentWeatherReportMapper = new CurrentWeatherReportMapper();
        List<CurrentWeatherReport> weatherReports = new ArrayList<>();

        List<CurrentWeatherResponse> theeDayForecast
                = combineHourlyForecastsIntoDailyForecasts(forecastResponse.getWeatherResponse());

        for (var dailyForecast : theeDayForecast) {
            weatherReports.add(currentWeatherReportMapper.mapCurrentWeather(dailyForecast));
        };

        return weatherReports;
    }

    private List<CurrentWeatherResponse> combineHourlyForecastsIntoDailyForecasts(List<CurrentWeatherResponse> weatherReports) {
        List<CurrentWeatherResponse> threeDayForecast = new ArrayList<>();

        for (int daysFromToday = 1; daysFromToday < 4; daysFromToday++) {
            LocalDate date = LocalDate.now().plusDays(daysFromToday);

            List<CurrentWeatherResponse> oneDayForecasts = getOneDayForecast(weatherReports, date);
            CurrentWeatherResponse oneDayForecast = combineOneDayForecastsIntoSingleForecast(oneDayForecasts);

            threeDayForecast.add(oneDayForecast);
        }
        return threeDayForecast;
    }

    private CurrentWeatherResponse combineOneDayForecastsIntoSingleForecast(List<CurrentWeatherResponse> forecasts) {
        CurrentWeatherResponse singleForecast = new CurrentWeatherResponse();

            singleForecast.setWeatherDetails(getAverageWeatherDetails(forecasts));
            singleForecast.setEpochDate(forecasts.get(0).getEpochDate());

        return singleForecast;
    }

    private List<CurrentWeatherResponse> getOneDayForecast(List<CurrentWeatherResponse> weatherReports, LocalDate date) {
        List<CurrentWeatherResponse> oneDayForecasts = weatherReports.stream()
                        .filter(threeHourForecast -> {
                            if (threeHourForecast.getEpochDate() != null)
                                return Objects.equals(getDate(threeHourForecast.getEpochDate()), date);
                            return false;
                        }).collect(Collectors.toList());

        if (oneDayForecasts.isEmpty()){
            throw new DateNotFoundException();
        }

        return oneDayForecasts;
    }

    private CurrentWeatherResponse.WeatherDetails getAverageWeatherDetails(List<CurrentWeatherResponse> forecasts) {
        CurrentWeatherResponse.WeatherDetails weatherDetails = new CurrentWeatherResponse.WeatherDetails();

        int divider = forecasts.size();

        weatherDetails.setTemperature(forecasts.stream()
                .mapToInt(weather -> weather.getWeatherDetails().getTemperature()).sum() / divider);
        weatherDetails.setHumidity(forecasts.stream()
                .mapToInt(weather -> weather.getWeatherDetails().getHumidity()).sum() / divider);
        weatherDetails.setPressure(forecasts.stream()
                .mapToInt(weather -> weather.getWeatherDetails().getPressure()).sum() / divider);

        return weatherDetails;
    }

    private LocalDate getDate(Long timeInSeconds) {
        return Instant.ofEpochSecond(timeInSeconds).atZone(ZoneId.systemDefault()).toLocalDate();
    }
}
