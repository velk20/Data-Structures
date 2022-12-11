package org.softuni.exam.structures;

import org.softuni.exam.entities.Deliverer;
import org.softuni.exam.entities.Package;

import java.util.*;
import java.util.stream.Collectors;

public class DeliveriesManagerImpl implements DeliveriesManager {

    private Map<String, Package> packageMap;
    private Map<String, Deliverer> delivererMap;
    private Map<String, List<Package>> deliversWithPackages;

    public DeliveriesManagerImpl() {
        this.packageMap = new LinkedHashMap<>();
        this.delivererMap = new LinkedHashMap<>();
        this.deliversWithPackages = new LinkedHashMap<>();
    }

    @Override
    public void addDeliverer(Deliverer deliverer) {
        if (this.contains(deliverer)) {
            throw new IllegalArgumentException();
        }

        this.delivererMap.put(deliverer.getId(), deliverer);
        this.deliversWithPackages.put(deliverer.getId(), new ArrayList<>());
    }

    @Override
    public void addPackage(Package _package) {
        if (this.contains(_package)) {
            throw new IllegalArgumentException();
        }
        this.packageMap.put(_package.getId(), _package);
    }

    @Override
    public boolean contains(Deliverer deliverer) {
        return this.delivererMap.containsKey(deliverer.getId());
    }

    @Override
    public boolean contains(Package _package) {
        return this.packageMap.containsKey(_package.getId());
    }

    @Override
    public Iterable<Deliverer> getDeliverers() {
        return this.delivererMap.values();
    }

    @Override
    public Iterable<Package> getPackages() {
        return this.packageMap.values();
    }

    @Override
    public void assignPackage(Deliverer deliverer, Package _package) throws IllegalArgumentException {
        if (!this.contains(deliverer) || !this.contains(_package)) {
            throw new IllegalArgumentException();
        }

        this.deliversWithPackages.get(deliverer.getId()).add(_package);
        this.packageMap.get(_package.getId()).setDeliverer(deliverer);
    }

    @Override
    public Iterable<Package> getUnassignedPackages() {
        return this.packageMap
                .values()
                .stream()
                .filter(p -> p.getDeliverer() == null)
                .collect(Collectors.toList());
    }

    @Override
    public Iterable<Package> getPackagesOrderedByWeightThenByReceiver() {
        return this.packageMap.values()
                .stream()
                .sorted((p1, p2) -> {
                    double p1Weight = p1.getWeight();
                    double p2Weight = p2.getWeight();
                    if (p1Weight == p2Weight) {
                        return p1.getReceiver().compareTo(p2.getReceiver());
                    }

                    return Double.compare(p2Weight, p1Weight);

                })
                .collect(Collectors.toList());
    }

    @Override
    public Iterable<Deliverer> getDeliverersOrderedByCountOfPackagesThenByName() {
        return this.delivererMap.values()
                .stream()
                .sorted((d1,d2)->{
                    int d1Packages = this.deliversWithPackages.get(d1.getId()).size();
                    int d2Packages = this.deliversWithPackages.get(d2.getId()).size();

                    if (d1Packages == d2Packages) {
                        return d1.getName().compareTo(d2.getName());
                    }

                    return Integer.compare(d2Packages, d1Packages);
                }).collect(Collectors.toList());
    }
}
