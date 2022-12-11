package org.softuni.exam.structures;

import org.softuni.exam.entities.Airline;
import org.softuni.exam.entities.Flight;

import java.util.*;
import java.util.stream.Collectors;

public class AirlinesManagerImpl implements AirlinesManager {
    private Map<String, Airline> airlineMap;
    private Map<String, List<Flight>> airlineFlights;

    public AirlinesManagerImpl() {
        this.airlineMap = new HashMap<>();
        this.airlineFlights = new HashMap<>();
    }

    @Override
    public void addAirline(Airline airline) {
        if (this.contains(airline)) {
            throw new IllegalArgumentException();
        }
        this.airlineFlights.put(airline.getId(), new ArrayList<>());
        this.airlineMap.put(airline.getId(), airline);
    }

    @Override
    public void addFlight(Airline airline, Flight flight) {
        if (!this.contains(airline)) {
            throw new IllegalArgumentException();
        }
        List<Flight> flightList = this.airlineFlights.get(airline.getId());
        if (flightList.contains(flight)) {
            throw new IllegalArgumentException();
        }

        flight.setAirline(airline);
        this.airlineFlights.get(airline.getId()).add(flight);


    }

    @Override
    public boolean contains(Airline airline) {
        return this.airlineMap.containsKey(airline.getId());
    }

    @Override
    public boolean contains(Flight flight) {
        return this.airlineFlights.values()
                .stream()
                .flatMap(Collection::stream)
                .anyMatch(f -> f.getId().equals(flight.getId()));
    }

    @Override
    public void deleteAirline(Airline airline) throws IllegalArgumentException {
        if (!this.contains(airline)) {
            throw new IllegalArgumentException();
        }

        this.airlineFlights.remove(airline.getId());
        this.airlineMap.remove(airline.getId());
    }

    @Override
    public Iterable<Flight> getAllFlights() {
        return this.airlineFlights.values()
                .stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    @Override
    public Flight performFlight(Airline airline, Flight flight) throws IllegalArgumentException {
        if (!this.contains(airline) || !this.contains(flight)) {
            throw new IllegalArgumentException();
        }

        List<Flight> flights = this.airlineFlights.get(airline.getId());
        if (!flights.contains(flight)) {
            throw new IllegalArgumentException();
        }
        flights.get(flights.indexOf(flight)).setCompleted(true);
        return flights.get(flights.indexOf(flight));
    }

    @Override
    public Iterable<Flight> getCompletedFlights() {
        return this.airlineFlights.values()
                .stream()
                .flatMap(Collection::stream)
                .filter(Flight::isCompleted)
                .collect(Collectors.toList());
    }

    @Override
    public Iterable<Flight> getFlightsOrderedByNumberThenByCompletion() {
        return this.airlineFlights.values()
                .stream()
                .flatMap(Collection::stream)
                .sorted((f1,f2)->{
                    if (!f1.isCompleted()) {
                        return -1;
                    }
                    if (!f2.isCompleted()) {
                        return 1;
                    } else {
                        return f1.getNumber().compareTo(f2.getNumber());
                    }
                })
                .collect(Collectors.toList());
    }

    @Override
    public Iterable<Airline> getAirlinesOrderedByRatingThenByCountOfFlightsThenByName() {
        return this.airlineMap.values()
                .stream()
                .sorted((a1,a2)->{
                    double a1Rating = a1.getRating();
                    double a2Rating = a2.getRating();

                    if (a1Rating == a2Rating) {
                        int a1Size = this.airlineFlights.get(a1.getId()).size();
                        int a2Size = this.airlineFlights.get(a2.getId()).size();

                        if (a1Size == a2Size) {
                            return a1.getName().compareTo(a2.getName());
                        }

                        return Integer.compare(a2Size, a1Size);
                    }


                    return Double.compare(a2Rating, a1Rating);

                }).collect(Collectors.toList());
    }

    @Override
    public Iterable<Airline> getAirlinesWithFlightsFromOriginToDestination(String origin, String destination) {
        return this.airlineMap.values()
                .stream()
                .filter(a->{
                    List<Flight> flights = this.airlineFlights.get(a.getId());
                    return flights.stream()
                            .filter(f -> !f.isCompleted())
                            .anyMatch(f -> f.getOrigin().equals(origin) && f.getDestination().equals(destination));
                }).collect(Collectors.toList());
    }
}
