package services;

import exceptions.AirlineAlreadyExist;
import exceptions.AirlineNotFound;
import exceptions.FlightAlreadyExist;
import exceptions.FlightNotFound;
import models.Airline;
import models.Flight;
import repositories.AirlineRepository;

public class AirlineService {
    private final AirlineRepository airlineRepository;
    private final FlightService flightService;

    public AirlineService(AirlineRepository airlineRepository, FlightService flightService) {
        this.airlineRepository = airlineRepository;
        this.flightService = flightService;
    }

    public boolean isRegistered(Long id) {
        return airlineRepository.findById(id).isPresent();
    }

    public Airline register(Airline airline) throws AirlineAlreadyExist {
        if (isRegistered(airline.getId())) {
            throw new AirlineAlreadyExist("'" + airline.getName() + "'" + " Already exist");
        }

        return airlineRepository.save(airline);
    }

    public Flight registerFlight(Flight flight) throws FlightAlreadyExist, AirlineNotFound {
        if (!isRegistered(flight.getAirline().getId())) {
            throw new AirlineNotFound("'" + flight.getAirline() + "'" + " Airline not found");
        }

        Flight updatedFlight = flightService.register(flight);
        updatedFlight.getAirline().addFlight(updatedFlight);
        return updatedFlight;
    }

    public void addService(Long flightId, String service) throws FlightNotFound {
        flightService.addService(flightId, service);
    }
}
