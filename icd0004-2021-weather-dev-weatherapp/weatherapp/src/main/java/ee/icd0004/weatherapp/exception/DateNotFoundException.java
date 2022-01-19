package ee.icd0004.weatherapp.exception;

public class DateNotFoundException extends RuntimeException {
    public DateNotFoundException(){
        super("ForecastReport has no date.");
    }
}
