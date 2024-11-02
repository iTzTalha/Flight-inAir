package models;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Airline extends BaseModel {
    private final String name;
    private final Set<Flight> flights;

    public Airline(String name) {
        this.name = name;
        flights = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public Set<Flight> getFlights() {
        return flights;
    }

    public void addFlight(Flight flight) {
        flights.add(flight);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Airline airline = (Airline) o;
        return Objects.equals(name, airline.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
