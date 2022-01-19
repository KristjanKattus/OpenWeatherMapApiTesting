package ee.icd0004.weatherapp.model;

import lombok.Data;

@Data
public class MainDetails {
    private String city;
    private String coordinates;
    private String temperatureUnit;
}
