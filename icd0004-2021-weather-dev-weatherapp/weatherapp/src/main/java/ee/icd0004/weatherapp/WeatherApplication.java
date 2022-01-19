package ee.icd0004.weatherapp;

import java.io.IOException;

public class WeatherApplication {

    private final static WeatherApp APP = new WeatherApp();

    public static void main(String[] args) throws IOException {
        APP.writeWeatherReportsToFiles();
    }
}
