package ee.icd0004.weatherapp;

import ee.icd0004.weatherapp.fileparser.FileParser;
import ee.icd0004.weatherapp.mapper.CurrentWeatherReportMapper;
import ee.icd0004.weatherapp.mapper.ForecastReportMapper;
import ee.icd0004.weatherapp.mapper.MainDetailsMapper;
import ee.icd0004.weatherapp.model.WeatherReport;
import ee.icd0004.weatherapp.weatherapi.CurrentWeatherResponse;
import ee.icd0004.weatherapp.weatherapi.ForecastResponse;
import ee.icd0004.weatherapp.weatherapi.WeatherApi;
import lombok.extern.java.Log;

import java.io.IOException;
import java.util.List;

@Log
public class WeatherApp {

    private final WeatherApi weatherApi = new WeatherApi();
    private final FileParser fileParser = new FileParser();
    private final MainDetailsMapper mainDetailsMapper = new MainDetailsMapper();
    private final CurrentWeatherReportMapper currentWeatherReportMapper = new CurrentWeatherReportMapper();
    private final ForecastReportMapper forecastReportMapper = new ForecastReportMapper();

    public WeatherReport getWeatherReport(String city) {
        CurrentWeatherResponse currentWeatherResponse = weatherApi.getCurrentWeatherResponse(city);
        ForecastResponse forecastResponse = weatherApi.getForecastResponse(city);
        return decorateWeatherReport(currentWeatherResponse, forecastResponse);
    }

    private WeatherReport decorateWeatherReport(CurrentWeatherResponse currentWeatherResponse, ForecastResponse forecastResponse) {
        WeatherReport weatherReport = new WeatherReport();
        weatherReport.setMainDetails(mainDetailsMapper.mapDetails(currentWeatherResponse));
        weatherReport.setCurrentWeatherReport(currentWeatherReportMapper.mapCurrentWeather(currentWeatherResponse));
        weatherReport.setForecastReport(forecastReportMapper.mapForecast(forecastResponse));
        return weatherReport;
    }

    public void writeWeatherReportsToFiles() throws IOException {
        List<String> cityNames = fileParser.readCitiesFromFile();
        for (String cityName : cityNames) {
            WeatherReport reportForCity = getWeatherReportIgnoringException(cityName);
            if (reportForCity != null) {
                fileParser.writeWeatherReportToFile(reportForCity);
            }
        }
    }

    private WeatherReport getWeatherReportIgnoringException(String cityName) {
        WeatherReport reportForCity = null;
        try {
            reportForCity = getWeatherReport(cityName);
        } catch (Exception e) {
            log.severe(String.valueOf(e));
            log.severe("Weather info for " + cityName + " not found.");
        }
        return reportForCity;
    }

}
