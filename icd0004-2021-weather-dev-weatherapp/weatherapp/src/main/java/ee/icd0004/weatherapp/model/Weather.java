package ee.icd0004.weatherapp.model;

import lombok.Data;

@Data
public class Weather {
    private int temperature;
    private int pressure;
    private int humidity;
}
