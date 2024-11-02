import exceptions.AirlineAlreadyExist;
import exceptions.AirlineNotFound;
import exceptions.FlightAlreadyExist;
import exceptions.FlightNotFound;
import models.Airline;
import models.Flight;
import repositories.AirlineRepository;
import repositories.FlightRepository;
import services.AirlineService;
import services.FlightSearchService;
import services.FlightService;

public class Driver {
    public static void main(String[] args) {
        final FlightRepository flightRepository = new FlightRepository();
        final FlightService flightService = new FlightService(flightRepository);

        final AirlineRepository airlineRepository = new AirlineRepository();
        final AirlineService airlineService = new AirlineService(airlineRepository, flightService);

        try {
            Airline jetAir = airlineService.register(new Airline("JetAir"));
            Airline delta = airlineService.register(new Airline("Delta"));
            Airline indigo = airlineService.register(new Airline("IndiGo"));

            try {
                Flight JA_DEL_BLR = airlineService.registerFlight(new Flight("JA", "DEL", "BLR", 500, jetAir));
                Flight JA_BLR_LON = airlineService.registerFlight(new Flight("JA", "BLR", "LON", 1000, jetAir));
                Flight JA_LON_NYC = airlineService.registerFlight(new Flight("JA", "LON", "NYC", 2000, jetAir));

                Flight DL_DEL_LON = airlineService.registerFlight(new Flight("DL", "DEL", "LON", 2000, delta));
                Flight DL_LON_NYC = airlineService.registerFlight(new Flight("DL", "LON", "NYC", 2000, delta));

                Flight IG_LON_NYC = airlineService.registerFlight(new Flight("IG", "LON", "NYC", 2500, indigo));
                Flight IG_DEL_BLR = airlineService.registerFlight(new Flight("IG", "DEL", "BLR", 600, indigo));
                Flight IG_BLR_PAR = airlineService.registerFlight(new Flight("IG", "BLR", "PAR", 800, indigo));
                Flight IG_PAR_LON = airlineService.registerFlight(new Flight("IG", "PAR", "LON", 300, indigo));

                try {
                    airlineService.addService(IG_LON_NYC.getId(), "meal");
                    airlineService.addService(IG_DEL_BLR.getId(), "meal");
                    airlineService.addService(IG_BLR_PAR.getId(), "meal");
                    airlineService.addService(IG_PAR_LON.getId(), "meal");
                } catch (FlightNotFound e) {
                    System.out.println(e.getMessage());
                    throw new RuntimeException(e);
                }
            } catch (FlightAlreadyExist | AirlineNotFound e) {
                System.out.println(e.getMessage());
                throw new RuntimeException(e);
            }
        } catch (AirlineAlreadyExist e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }

        final FlightSearchService flightSearchService = new FlightSearchService(flightService);

        flightSearchService.search("DEL", "NYC");

        flightSearchService.search("DEL", "NYC", "meal");
    }
}
