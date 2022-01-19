package ee.icd0004.weatherapp.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = false)
@Data
public class CurrentWeatherReport extends Weather{
    private LocalDate date;
}
