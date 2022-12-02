package barbershopjava;

import java.util.*;
import java.util.stream.Collectors;

public class BarberShopImpl implements BarberShop {

    private Map<String,Barber> barbersByNames;
    private Map<String,List<Client>> barbersClients;
    private Map<String,Client> clientsByNames;

    public BarberShopImpl() {
        this.barbersByNames = new HashMap<>();
        this.barbersClients = new HashMap<>();
        this.clientsByNames = new HashMap<>();
    }

    @Override
    public void addBarber(Barber b) {
        if (barbersByNames.containsKey(b.name)) {
            throw new IllegalArgumentException();
        }
        this.barbersByNames.put(b.name, b);
        this.barbersClients.put(b.name, new ArrayList<>());
    }

    @Override
    public void addClient(Client c) {
        if (clientsByNames.containsKey(c.name)) {
            throw new IllegalArgumentException();
        }
        this.clientsByNames.put(c.name,c);
    }

    @Override
    public boolean exist(Barber b) {
        return barbersByNames.containsKey(b.name);
    }

    @Override
    public boolean exist(Client c) {
        return clientsByNames.containsKey(c.name);
    }

    @Override
    public Collection<Barber> getBarbers() {
        return this.barbersByNames.values();
    }

    @Override
    public Collection<Client> getClients() {
        return this.clientsByNames.values();
    }

    @Override
    public void assignClient(Barber b, Client c) {
        if (!clientsByNames.containsKey(c.name) || !this.barbersByNames.containsKey(b.name)) {
            throw new IllegalArgumentException();
        }
        c.barber = b;
        barbersClients.get(b.name).add(c);
    }

    @Override
    public void deleteAllClientsFrom(Barber b) {
        if (!this.barbersByNames.containsKey(b.name)) {
            throw new IllegalArgumentException();
        }

        this.barbersClients.get(b.name).clear();
    }

    @Override
    public Collection<Client> getClientsWithNoBarber() {
        return this.clientsByNames.values()
                .stream()
                .filter(c -> c.barber == null)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Barber> getAllBarbersSortedWithClientsCountDesc() {
        return getBarbers().stream()
                .sorted((b1, b2) -> {
                    int firstClients = barbersClients.get(b1.name).size();
                    int secondClients = barbersClients.get(b2.name).size();

                    return Integer.compare(secondClients, firstClients);
                }).collect(Collectors.toList());
    }

    @Override
    public Collection<Barber> getAllBarbersSortedWithStarsDescendingAndHaircutPriceAsc() {
        Comparator<Barber> compareByName = Comparator
                .comparing(Barber::getStars)
                .reversed()
                .thenComparing(Barber::getHaircutPrice);

        return this.barbersByNames.values()
                .stream()
                .sorted(compareByName)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Client> getClientsSortedByAgeDescAndBarbersStarsDesc() {
//        Comparator<Client> compareByName = Comparator
//                .comparing(Client::getAge)
//                .reversed()
//                .thenComparingInt(client -> client.getBarber().getStars())
//                .reversed();

        return this.clientsByNames.values()
                .stream()
                .sorted((c1,c2)->{
                    int c1Age = c1.getAge();
                    int c2Age = c2.getAge();

                    if (c1Age == c2Age) {
                        return Integer.compare(c2.getBarber().stars, c1.getBarber().stars);
                    }

                    return Integer.compare(c2Age, c1Age);
                })
                .collect(Collectors.toList());
    }
}
