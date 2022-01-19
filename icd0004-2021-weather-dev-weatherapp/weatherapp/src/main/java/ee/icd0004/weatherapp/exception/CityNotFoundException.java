package ee.icd0004.weatherapp.exception;

public class CityNotFoundException extends RuntimeException {
    public CityNotFoundException(){
        super("Provided city name not found.");
    }
}
