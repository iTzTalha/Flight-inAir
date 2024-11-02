package exceptions;

public class FlightAlreadyExist extends Exception {
    public FlightAlreadyExist() {
    }

    public FlightAlreadyExist(String message) {
        super(message);
    }
}
