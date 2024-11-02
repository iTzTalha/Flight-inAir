package repositories;

import models.Flight;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class FlightRepository {
    private static Long ID = 1L;
    private final Map<Long, Flight> db = new ConcurrentHashMap<>();
    private final Map<String, List<Flight>> graph = new ConcurrentHashMap<>();

    public Flight save(Flight flight) {
        flight.setId(ID++);
        db.put(flight.getId(), flight);
        return flight;
    }

    public Optional<Flight> find(Flight flight) {
        return db.values().stream().filter(o -> o.equals(flight)).findFirst();
    }

    public Optional<Flight> findById(Long id) {
        return db.values().stream().filter(flight -> flight.getId().equals(id)).findFirst();
    }

    public List<Flight> getAllByService(String service) {
        return db.values().stream().filter(flight -> flight.getServices().contains(service)).collect(Collectors.toList());
    }

    public void updateGraph(Flight flight) {
        graph.putIfAbsent(flight.getOrigin(), new ArrayList<>());

        List<Flight> flights = graph.get(flight.getOrigin());
        Optional<Flight> flightOptional = flights
                .stream()
                .filter(node -> node.getId().equals(flight.getId()))
                .findFirst();

        if (!flightOptional.isPresent()) {
            flights.add(flight);
        }
    }

    public Map<String, List<Flight>> getGraph() {
        Map<String, List<Flight>> immutableGraph = new HashMap<>();
        for (String key : graph.keySet()) {
            immutableGraph.put(key, Collections.unmodifiableList(graph.get(key)));
        }
        return Collections.unmodifiableMap(immutableGraph);
    }
}
