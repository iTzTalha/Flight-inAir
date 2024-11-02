package exceptions;

public class FlightNotFound extends Exception {
    public FlightNotFound() {
    }

    public FlightNotFound(String message) {
        super(message);
    }
}
