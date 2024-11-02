package services;

import models.Flight;

import java.util.*;
import java.util.stream.Collectors;

public class FlightSearchService {
    private final Map<String, List<Flight>> flightGraph;

    public FlightSearchService(FlightService flightService) {
        this.flightGraph = flightService.getFlights();
    }

    public void search(String origin, String destination, String... services) {
        Set<String> serviceSet = new HashSet<>(Arrays.asList(services));

        List<List<Flight>> flightsWithMinHops = searchByHops(origin, destination, serviceSet);

        double totalCost;
        int totalHops;

        for (List<Flight> list : flightsWithMinHops) {
            System.out.print("Route with Minimum Hops: ");

            totalCost = 0;
            totalHops = 0;

            for (Flight flight : list) {
                System.out.print(flight.getOrigin() + " to " + flight.getDestination() + " via " + flight.getAirline().getName() + " for " + flight.getFare() + " ");
                totalCost += flight.getFare();
                totalHops++;
            }

            System.out.print("Total Flights = " + totalHops + " ");
            System.out.print("Total Cost = " + totalCost);

            System.out.println();
        }

        List<List<Flight>> flightsWithCheapestRoute = searchByCost(origin, destination, serviceSet);

        for (List<Flight> list : flightsWithCheapestRoute) {
            System.out.print("Cheapest Route: ");

            totalCost = 0;
            totalHops = 0;

            for (Flight flight : list) {
                System.out.print(flight.getOrigin() + " to " + flight.getDestination() + " via " + flight.getAirline().getName() + " for " + flight.getFare() + " ");
                totalCost += flight.getFare();
                totalHops++;
            }

            System.out.print("Total Flights = " + totalHops + " ");
            System.out.print("Total Cost = " + totalCost);

            System.out.println();
        }
    }

    private OptionalDouble getMinimumFare(String origin, String destination, Set<String> services) {
        String source = "null@" + origin;

        Queue<Map.Entry<String, Double>> minHeap = new PriorityQueue<>(Map.Entry.comparingByValue());
        minHeap.offer(new AbstractMap.SimpleEntry<>(source, 0d));

        Map<String, Double> fares = new HashMap<>();
        fares.put(source, 0d);

        while (!minHeap.isEmpty()) {
            Map.Entry<String, Double> currentNode = minHeap.poll();
            String currentDestination = currentNode.getKey().split("@")[1];
            Double currentFare = currentNode.getValue();

            if (currentDestination.equals(destination)) continue;

            for (Flight nbr : flightGraph.getOrDefault(currentDestination, new ArrayList<>())) {
                if (!services.isEmpty() && !nbr.getServices().containsAll(services)) continue;

                String nextDestination = nbr.getAirline().getName() + "@" + nbr.getDestination();
                Double nextFare = currentFare + nbr.getFare();

                if (nextFare < fares.getOrDefault(nextDestination, Double.MAX_VALUE)) {
                    fares.put(nextDestination, nextFare);
                    minHeap.offer(new AbstractMap.SimpleEntry<>(nextDestination, nextFare));
                }
            }
        }

        return fares.keySet().stream().filter(d -> d.contains(destination)).mapToDouble(fares::get).min();
    }

    private List<List<Flight>> searchByCost(String origin, String destination, Set<String> services) {
        OptionalDouble minimumFareOptional = getMinimumFare(origin, destination, services);
        if (!minimumFareOptional.isPresent()) return new ArrayList<>();

        List<List<Flight>> flights = new ArrayList<>();
        searchByCost(origin, destination, minimumFareOptional.getAsDouble(), services, new HashSet<>(), new ArrayList<>(), flights);

        OptionalInt minOptional = flights.stream().mapToInt(List::size).min();
        if (!minOptional.isPresent()) return new ArrayList<>();

        int minHops = minOptional.getAsInt();
        return flights.stream().filter(flightNodes -> flightNodes.size() == minHops).collect(Collectors.toList());
    }

    private void searchByCost(String origin, String destination, double minimumFare, Set<String> services, Set<String> visited, List<Flight> list, List<List<Flight>> flights) {
        if (origin.equals(destination) && minimumFare == 0) {
            flights.add(new ArrayList<>(list));
            return;
        }
        if (minimumFare <= 0) return;

        for (Flight nbr : flightGraph.getOrDefault(origin, new ArrayList<>())) {
            if (!services.isEmpty() && !nbr.getServices().containsAll(services)) continue;

            if (!visited.contains(nbr.getDestination())) {
                visited.add(origin);

                list.add(nbr);
                searchByCost(nbr.getDestination(), destination, minimumFare - nbr.getFare(), services, visited, list, flights);
                list.remove(list.size() - 1);
            }
        }
    }

    private OptionalInt getMinimumHops(String origin, String destination, Set<String> services) {
        String source = "null@" + origin;

        Queue<Map.Entry<String, Integer>> minHeap = new PriorityQueue<>(Map.Entry.comparingByValue());
        minHeap.offer(new AbstractMap.SimpleEntry<>(source, 0));

        Map<String, Integer> fares = new HashMap<>();
        fares.put(source, 0);

        while (!minHeap.isEmpty()) {
            Map.Entry<String, Integer> currentNode = minHeap.poll();
            String currentDestination = currentNode.getKey().split("@")[1];
            int currentFare = currentNode.getValue();

            if (currentDestination.equals(destination)) continue;

            for (Flight nbr : flightGraph.getOrDefault(currentDestination, new ArrayList<>())) {
                if (!services.isEmpty() && !nbr.getServices().containsAll(services)) continue;

                String nextDestination = nbr.getAirline().getName() + "@" + nbr.getDestination();
                int nextFare = currentFare + 1;

                if (nextFare < fares.getOrDefault(nextDestination, Integer.MAX_VALUE)) {
                    fares.put(nextDestination, nextFare);
                    minHeap.offer(new AbstractMap.SimpleEntry<>(nextDestination, nextFare));
                }
            }
        }

        return fares.keySet().stream().filter(d -> d.contains(destination)).mapToInt(fares::get).min();
    }

    private List<List<Flight>> searchByHops(String origin, String destination, Set<String> services) {
        OptionalInt minimumHopsOptional = getMinimumHops(origin, destination, services);
        if (!minimumHopsOptional.isPresent()) return new ArrayList<>();

        List<List<Flight>> flights = new ArrayList<>();
        searchByHops(origin, destination, minimumHopsOptional.getAsInt(), services, new HashSet<>(), new ArrayList<>(), flights);

        Optional<Double> minFareOptional = flights.stream().map(flightNodes -> flightNodes.stream().mapToDouble(Flight::getFare).sum()).min(Double::compareTo);
        if (!minFareOptional.isPresent()) return new ArrayList<>();

        double minFare = minFareOptional.get();

        return flights
                .stream()
                .filter(flightNodes ->
                        flightNodes.stream().mapToDouble(Flight::getFare).sum() == minFare).collect(Collectors.toList());
    }

    private void searchByHops(String origin, String destination, double minimumHops, Set<String> services, Set<String> visited, List<Flight> list, List<List<Flight>> flights) {
        if (origin.equals(destination) && 0 == minimumHops) {
            flights.add(new ArrayList<>(list));
            return;
        }
        if (minimumHops <= 0) return;

        for (Flight nbr : flightGraph.getOrDefault(origin, new ArrayList<>())) {
            if (!services.isEmpty() && !nbr.getServices().containsAll(services)) continue;

            if (!visited.contains(nbr.getDestination())) {
                visited.add(origin);

                list.add(nbr);
                searchByHops(nbr.getDestination(), destination, minimumHops - 1, services, visited, list, flights);
                list.remove(list.size() - 1);
            }
        }
    }
}
