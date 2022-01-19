package ee.icd0004.weatherapp.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class CitiesFile implements Serializable {
    private List<String> cities;
}
