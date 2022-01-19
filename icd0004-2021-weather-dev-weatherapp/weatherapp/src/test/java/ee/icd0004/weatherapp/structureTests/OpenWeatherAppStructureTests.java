package ee.icd0004.weatherapp.structureTests;

import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasKey;

public class OpenWeatherAppStructureTests {

    private final static String BASE_URL = "https://api.openweathermap.org/data/2.5/weather";
    private static final String APP_ID = "d80964ece84eec2e6d11ed9fad1b2c7a";

    @Test
    public void shouldReturnCityName_whenCityNameIsGiven() {
        getResponse()
                .body("$", hasKey("name"))
                .body("name", equalTo("Tallinn"));
    }

    @Test
    public void shouldHaveCoordinatesBlock_whenCorrectCityNameIsGiven() {
        getResponse()
                .body("$", hasKey("name"))
                .body("name", equalTo("Tallinn"))
                .body("$", hasKey("coord"))
                .body("coord", hasKey("lon"))
                .body("coord", hasKey("lat"));
    }

    @Test
    public void shouldHaveTemperatureField_whenCorrectCityNameIsGiven() {
        getResponse()
                .body("$", hasKey("name"))
                .body("name", equalTo("Tallinn"))
                .body("$", hasKey("main"))
                .body("main", hasKey("temp"));
    }
    @Test
    public void shouldHaveHumidityField_whenCorrectCityNameIsGiven() {
        getResponse()
                .body("$", hasKey("name"))
                .body("name", equalTo("Tallinn"))
                .body("$", hasKey("main"))
                .body("main", hasKey("humidity"));
    }
    @Test
    public void shouldHavePressureField_whenCorrectCityNameIsGiven() {
        getResponse()
                .body("$", hasKey("name"))
                .body("name", equalTo("Tallinn"))
                .body("$", hasKey("main"))
                .body("main", hasKey("pressure"));
    }

    private ValidatableResponse getResponse() {
        return given()
                .queryParam("q", "Tallinn")
                .queryParam("appid", APP_ID)
                .queryParam("units", "metric")
                .when()
                .get(BASE_URL)
                .then();
    }
}
