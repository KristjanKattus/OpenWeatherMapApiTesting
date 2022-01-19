package ee.icd0004.weatherapp.weatherapi;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import ee.icd0004.weatherapp.exception.CityNotFoundException;
import ee.icd0004.weatherapp.weatherapi.client.JerseyClient;

import static java.net.HttpURLConnection.HTTP_NOT_FOUND;

public class WeatherApi {

    private final Client client = JerseyClient.getClient();
    private final static String BASE_OWM_URL = "https://api.openweathermap.org/data/2.5";
    private final static String CURRENT_WEATHER_API = BASE_OWM_URL + "/weather";
    private final static String FORECAST_WEATHER_API = BASE_OWM_URL + "/forecast";
    private final static String unit = "metric";
    private final static String API_TOKEN = "d80964ece84eec2e6d11ed9fad1b2c7a";

    public CurrentWeatherResponse getCurrentWeatherResponse(String cityName) {

        CurrentWeatherResponse weatherResponse = getClientResponse(CURRENT_WEATHER_API, cityName)
                .getEntity(CurrentWeatherResponse.class);

        weatherResponse.setUnit(unit);

        return weatherResponse;
    }

    public ForecastResponse getForecastResponse(String cityName) {
        return getClientResponse(FORECAST_WEATHER_API, cityName)
                .getEntity(ForecastResponse.class);
    }

    private ClientResponse getClientResponse (String apiUrl, String cityName) {
        ClientResponse response = client.resource(apiUrl)
                .queryParam("appid", API_TOKEN)
                .queryParam("q", cityName)
                .queryParam("units", unit)

                .get(ClientResponse.class);

        validateResponse(response);

        return response;
    }

    private void validateResponse(ClientResponse response){
        if(response.getStatus() == HTTP_NOT_FOUND) {
            throw new CityNotFoundException();
        }
    }

}
