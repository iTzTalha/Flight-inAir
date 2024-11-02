package repositories;

import models.Airline;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class AirlineRepository {
    private static Long ID = 1L;
    private final Map<Long, Airline> db = new ConcurrentHashMap<>();

    public Airline save(Airline airline) {
        airline.setId(ID++);
        db.put(airline.getId(), airline);
        return airline;
    }

    public Optional<Airline> findById(Long airlineId) {
        return db.values().stream().filter(airline -> airline.getId().equals(airlineId)).findFirst();
    }
}
