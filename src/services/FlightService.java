package services;

import exceptions.FlightAlreadyExist;
import exceptions.FlightNotFound;
import models.Flight;
import repositories.FlightRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class FlightService {
    private final FlightRepository flightRepository;

    public FlightService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    public Flight register(Flight flight) throws FlightAlreadyExist {
        if (flightRepository.find(flight).isPresent()) {
            throw new FlightAlreadyExist("Airline: " + flight.getAirline() + " Flight: " + flight.getName() + " already exist");
        }

        final Flight updatedFlight = flightRepository.save(flight);

        flightRepository.updateGraph(updatedFlight);

        return updatedFlight;
    }

    public void addService(Long flightId, String service) throws FlightNotFound {
        Optional<Flight> flightOptional = flightRepository.findById(flightId);

        if (!flightOptional.isPresent()) {
            throw new FlightNotFound("Flight: " + flightId + " not found");
        }

        Flight flight = flightOptional.get();
        flight.addService(service);
    }

    public List<Flight> getAllByService(String service) {
        return flightRepository.getAllByService(service);
    }

    public Map<String, List<Flight>> getFlights() {
        return flightRepository.getGraph();
    }
}
