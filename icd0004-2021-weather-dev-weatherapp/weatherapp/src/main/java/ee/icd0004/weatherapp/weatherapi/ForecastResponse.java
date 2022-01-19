package ee.icd0004.weatherapp.weatherapi;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ForecastResponse {

    @JsonProperty("list")
    private List<CurrentWeatherResponse> weatherResponse;

    @JsonProperty("city")
    private City City;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class City {
        private String name;
        @JsonProperty("coord")
        private CurrentWeatherResponse.Coordinates coordinates;
    }
}
