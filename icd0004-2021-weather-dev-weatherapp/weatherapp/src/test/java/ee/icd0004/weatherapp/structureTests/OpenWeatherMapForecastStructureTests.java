package ee.icd0004.weatherapp.structureTests;

import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class OpenWeatherMapForecastStructureTests {
    private final static String BASE_URL = "https://api.openweathermap.org/data/2.5/forecast";
    private static final String APP_ID = "d80964ece84eec2e6d11ed9fad1b2c7a";

    @Test
    public void shouldHaveCityField_whenCorrectCityNameIsGiven() {
        getResponse()
                .body("$", hasKey("city"))
                .body("city", hasKey("name"))
                .body("city.name", equalTo("Tallinn"));
    }

    @Test
    public void shouldHaveCoordinatesField_WhenCorrectCityNameIsGiven() {
        getResponse()
                .body("$", hasKey("city"))
                .body("city", hasKey("coord"))
                .body("city.coord", hasKey("lat"))
                .body("city.coord", hasKey("lon"));
    }

    @Test
    public void shouldHaveForecastList_WhenCorrectCityNameIsGiven() {
        getResponse()
                .body("$", hasKey("list"));
    }

    @Test
    public void forecastListShouldHaveFiveDayForecast_WhenCorrectCityNameIsGiven() {
        getResponse()
                .body("$", hasKey("list"))
                .body("list", hasSize(40));
    }

    @Test
    public void eachForecastReportShouldHaveCorrectFields_WhenCorrectCityNameIsGiven() {
        ValidatableResponse response = getResponse();

        for (int i = 0; i < 40; i++) {
            response
                    .body(String.format("list[%s]", i), hasKey("dt"))
                    .body(String.format("list[%s].main", i), hasKey("temp"))
                    .body(String.format("list[%s].main", i), hasKey("pressure"))
                    .body(String.format("list[%s].main", i), hasKey("humidity"));
        }
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
