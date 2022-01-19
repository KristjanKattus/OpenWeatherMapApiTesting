package ee.icd0004.weatherapp.smoketests;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static io.restassured.RestAssured.given;
import static java.net.HttpURLConnection.*;

public class OpenWeatherMapTests {

    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/";
    private static final String APP_ID = "d80964ece84eec2e6d11ed9fad1b2c7a";

    @ParameterizedTest
    @ValueSource(strings = {"weather", "forecast"})
    public void whenCalledWithoutApiKey_returnsHttpUnauthorized(String parameter) {
        given()
                .when()
                .get(BASE_URL + parameter)
                .then()
                .statusCode(HTTP_UNAUTHORIZED);
    }

    @ParameterizedTest
    @ValueSource(strings = {"weather", "forecast"})
    public void shouldReturnHttpOk_whenCityNameIsGiven(String parameter) {
        given()
                .queryParam("q", "Tallinn")
                .queryParam("appid", APP_ID)
                .queryParam("units", "metric")
                .when()
                .get(BASE_URL + parameter)
                .then()
                .statusCode(HTTP_OK);
    }
    @ParameterizedTest
    @ValueSource(strings = {"weather", "forecast"})
    public void shouldReturnHttpBadRequest_whenNoCityNameIsGiven(String parameter) {
        given()
                .queryParam("appid", APP_ID)
                .queryParam("units", "metric")
                .when()
                .get(BASE_URL + parameter)
                .then()
                .statusCode(HTTP_BAD_REQUEST);
    }
}
