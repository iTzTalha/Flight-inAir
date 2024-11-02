package models;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Flight extends BaseModel {
    private final String name;
    private final String origin;
    private final String destination;
    private final double fare;
    private final Airline airline;
    private final Set<String> services;

    public Flight(String name, String origin, String destination, double fare, Airline airline) {
        this.name = name;
        this.origin = origin;
        this.destination = destination;
        this.fare = fare;
        this.airline = airline;
        this.services = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    public double getFare() {
        return fare;
    }

    public Set<String> getService() {
        return services;
    }

    public void addService(String service) {
        services.add(service);
    }

    public Airline getAirline() {
        return airline;
    }

    public Set<String> getServices() {
        return services;
    }

    @Override
    public String toString() {
        return "Flight{" +
                "name='" + name + '\'' +
                ", origin='" + origin + '\'' +
                ", destination='" + destination + '\'' +
                ", fare=" + fare +
                ", airline=" + airline +
                ", services=" + services +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flight flight = (Flight) o;
        return Double.compare(fare, flight.fare) == 0 && Objects.equals(name, flight.name) && Objects.equals(origin, flight.origin) && Objects.equals(destination, flight.destination) && airline.equals(flight.getAirline());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, origin, destination, fare, airline);
    }
}
