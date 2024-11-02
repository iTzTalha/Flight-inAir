package exceptions;

public class AirlineNotFound extends Exception {
    public AirlineNotFound() {
    }

    public AirlineNotFound(String message) {
        super(message);
    }
}
