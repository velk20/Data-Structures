package core;

import models.Route;

import java.util.*;
import java.util.stream.Collectors;

public class MoovItImpl implements MoovIt {
    private final Map<String, Route> routesById;
    private final Set<Route> routes;

    public MoovItImpl() {
        this.routesById = new LinkedHashMap<>();
        this.routes = new HashSet<>();
    }

    @Override
    public void addRoute(Route route) {
        if (this.routes.contains(route)) {
            throw new IllegalArgumentException();
        }

        this.routesById.put(route.getId(), route);
        this.routes.add(route);
    }

    @Override
    public void removeRoute(String routeId) {
        Route remove = this.routesById.remove(routeId);
        if (remove == null) {
            throw new IllegalArgumentException();
        }

        this.routes.remove(remove);
    }

    @Override
    public boolean contains(Route route) {
        return this.routes.contains(route);
    }

    @Override
    public int size() {
        return this.routesById.size();
    }

    @Override
    public Route getRoute(String routeId) {
        Route route = this.routesById.get(routeId);
        if (route == null) {
            throw new IllegalArgumentException();
        }
        return route;
    }

    @Override
    public void chooseRoute(String routeId) {
        Route route = this.getRoute(routeId);
        route.setPopularity(route.getPopularity() + 1);
    }

    @Override
    public Iterable<Route> searchRoutes(String startPoint, String endPoint) {
        return this.routesById.values()
                .stream()
                .filter(r-> new HashSet<>(r.getLocationPoints()).containsAll(List.of(startPoint,endPoint)) && r.getLocationPoints().indexOf(startPoint)< r.getLocationPoints().indexOf(endPoint))
                .sorted((l,r)->{
                    Boolean lIsFavorite = l.getIsFavorite();
                    Boolean rIsFavorite = r.getIsFavorite();
                    if (lIsFavorite == rIsFavorite) {
                        int lDistance =
                                l.getLocationPoints().indexOf(endPoint) - l.getLocationPoints().indexOf(startPoint);
                        int rDistance =
                                r.getLocationPoints().indexOf(endPoint) - r.getLocationPoints().indexOf(startPoint);
                        if (lDistance == rDistance) {
                            return Integer.compare(r.getPopularity(), l.getPopularity());
                        }

                        return Integer.compare(lDistance, rDistance);
                    }
                    return Boolean.compare(lIsFavorite,rIsFavorite);
                }).collect(Collectors.toList());
    }

    @Override
    public Iterable<Route> getFavoriteRoutes(String destinationPoint) {
       return this.routesById.values().stream()
                .filter(r -> r.getIsFavorite() && r.getLocationPoints().contains(destinationPoint) && r.getLocationPoints().indexOf(destinationPoint) != 0)
                .sorted((l,r)->{
                    Double lDistance = l.getDistance();
                    Double rDistance = r.getDistance();

                    if (Objects.equals(lDistance, rDistance)) {
                        return Integer.compare(r.getPopularity(), l.getPopularity());
                    }
                    return Double.compare(lDistance, rDistance);
                }).collect(Collectors.toList());

    }

    @Override
    public Iterable<Route> getTop5RoutesByPopularityThenByDistanceThenByCountOfLocationPoints() {
        return this.routesById.values()
                .stream()
                .sorted((l,r)->{
                    Integer lPopularity = l.getPopularity();
                    Integer rPopularity = r.getPopularity();

                    if (Objects.equals(lPopularity, rPopularity)) {
                        Double lDistance = l.getDistance();
                        Double rDistance = r.getDistance();

                        if (Objects.equals(lDistance, rDistance)) {
                            return Integer.compare(l.getLocationPoints().size(), r.getLocationPoints().size());
                        }
                        return Double.compare(lDistance, rDistance);
                    }

                    return Integer.compare(rPopularity, lPopularity);
                }).limit(5).collect(Collectors.toList());
    }
}
