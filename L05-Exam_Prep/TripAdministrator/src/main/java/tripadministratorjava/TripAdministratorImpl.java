package tripadministratorjava;

import java.util.*;
import java.util.stream.Collectors;

public class TripAdministratorImpl implements TripAdministrator {

    Map<String,Company> companyWithNames;
    Map<String, List<Trip>> companyWithTrips;
    Map<String,Trip> tripsWithIds;

    public TripAdministratorImpl() {
        this.companyWithNames = new LinkedHashMap<>();
        this.tripsWithIds = new LinkedHashMap<>();
        this.companyWithTrips = new LinkedHashMap<>();
    }

    @Override
    public void addCompany(Company c) {
        if (companyWithNames.containsKey(c.getName())) {
            throw new IllegalArgumentException();
        }
        this.companyWithTrips.put(c.name, new ArrayList<>());
        this.companyWithNames.put(c.name, c);

    }

    @Override
    public void addTrip(Company c, Trip t) {
        if (!companyWithNames.containsKey(c.getName())) {
            throw new IllegalArgumentException();
        }
        if (this.companyWithTrips.get(c.getName()).contains(t)) {
            throw new IllegalArgumentException();
        }
        if (this.companyWithNames.get(c.getName()).tripOrganizationLimit == this.companyWithTrips.get(c.getName()).size()) {
            throw new IllegalArgumentException();
        }
        this.tripsWithIds.put(t.getId(), t);
        this.companyWithTrips.get(c.getName()).add(t);
    }

    @Override
    public boolean exist(Company c) {
        return this.companyWithNames.containsKey(c.getName());
    }

    @Override
    public boolean exist(Trip t) {
        return this.tripsWithIds.containsKey(t.getId());
    }

    @Override
    public void removeCompany(Company c) {
        if (!this.companyWithNames.containsKey(c.getName())) {
            throw new IllegalArgumentException();
        }
        this.companyWithNames.remove(c.getName());
        List<Trip> trips = this.companyWithTrips.remove(c.getName());
        for (Trip trip : trips) {
            this.tripsWithIds.remove(trip.getId());
        }
    }

    @Override
    public Collection<Company> getCompanies() {
        return this.companyWithNames.values();
    }

    @Override
    public Collection<Trip> getTrips() {
        return this.tripsWithIds.values();
    }

    @Override
    public void executeTrip(Company c, Trip t) {
        if (!exist(c) || !exist(t)) {
            throw new IllegalArgumentException();
        }
        List<Trip> trips = companyWithTrips.get(c.getName());
        boolean removeIf = trips.removeIf(tr -> tr.id.equals(t.id));
        if (!removeIf) {
            throw new IllegalArgumentException();
        }

        tripsWithIds.remove(t.id);
    }

    @Override
    public Collection<Company> getCompaniesWithMoreThatNTrips(int n) {
        return getCompanies()
                .stream()
                .filter(c -> companyWithTrips.get(c.getName()).size() > n)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Trip> getTripsWithTransportationType(Transportation t) {
        return this.tripsWithIds.values().stream()
                .filter(trip -> trip.getTransportation().name().equals(t.name()))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Trip> getAllTripsInPriceRange(int lo, int hi) {
        return this.tripsWithIds.values()
                .stream()
                .filter(trip -> trip.getPrice() >= lo && trip.getPrice() <= hi)
                .collect(Collectors.toList());
    }
}
