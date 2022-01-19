package ee.icd0004.weatherapp.weatherapi;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrentWeatherResponse {
    @JsonProperty("coord")
    private Coordinates coordinates;
    @JsonProperty("main")
    private WeatherDetails weatherDetails;
    private String name;
    @JsonProperty("dt")
    private Long epochDate;

    @JsonIgnore
    private String unit;

    @Data
    public static class Coordinates {
        private String lon;
        private String lat;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class WeatherDetails {
        @JsonProperty("temp")
        private int temperature;
        private int pressure;
        private int humidity;
    }
}
