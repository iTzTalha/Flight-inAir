package exceptions;

public class AirlineAlreadyExist extends Exception {
    public AirlineAlreadyExist() {
    }

    public AirlineAlreadyExist(String message) {
        super(message);
    }
}
