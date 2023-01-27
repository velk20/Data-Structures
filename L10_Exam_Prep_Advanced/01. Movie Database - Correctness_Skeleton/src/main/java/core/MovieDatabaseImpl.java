package core;

import models.Movie;

import java.util.*;
import java.util.stream.Collectors;

public class MovieDatabaseImpl implements MovieDatabase {
    private Map<String, Movie> moviesById;
    private Map<String, Integer> moviesCountByActor;

    public MovieDatabaseImpl() {
        this.moviesById = new LinkedHashMap<>();
        this.moviesCountByActor = new HashMap<>();
    }

    @Override
    public void addMovie(Movie movie) {
        this.moviesById.put(movie.getId(), movie);
        for (String actor : movie.getActors()) {
            if (this.moviesCountByActor.containsKey(actor)) {
                this.moviesCountByActor.put(actor, this.moviesCountByActor.get(actor) + 1);
            }else{
                this.moviesCountByActor.put(actor, 1);
            }
        }
    }

    @Override
    public void removeMovie(String movieId) {
        if (this.moviesById.containsKey(movieId)) {
            throw new IllegalArgumentException();
        }

        this.moviesById.remove(movieId);
    }

    @Override
    public int size() {
        return this.moviesById.size();
    }

    @Override
    public boolean contains(Movie movie) {
        return this.moviesById.containsKey(movie.getId());
    }

    @Override
    public Iterable<Movie> getMoviesByActor(String actorName) {
        List<Movie> movies = this.moviesById.values()
                .stream()
                .filter(m -> m.getActors().contains(actorName))
                .sorted((l, r) -> {
                    double lRating = l.getRating();
                    double rRating = r.getRating();
                    if (lRating == rRating) {
                        return r.getReleaseYear() - l.getReleaseYear();
                    }
                    return Double.compare(rRating, lRating);
                }).collect(Collectors.toList());

        if (movies.isEmpty()) {
            throw new IllegalArgumentException();
        }
        return movies;
    }

    @Override
    public Iterable<Movie> getMoviesByActors(List<String> actors) {
        List<Movie> movies = this.moviesById.values()
                .stream()
                .filter(m -> new HashSet<>(m.getActors()).containsAll(actors))
                .sorted((l, r) -> {
                    double lRating = l.getRating();
                    double rRating = r.getRating();
                    if (lRating == rRating) {
                        return r.getReleaseYear() - l.getReleaseYear();
                    }
                    return Double.compare(rRating, lRating);
                }).collect(Collectors.toList());

        if (movies.isEmpty()) {
            throw new IllegalArgumentException();
        }
        return movies;
    }

    @Override
    public Iterable<Movie> getMoviesByYear(Integer releaseYear) {
        return this.moviesById.values()
                .stream()
                .filter(m->m.getReleaseYear() == releaseYear)
                .sorted(Comparator.comparing(Movie::getRating).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public Iterable<Movie> getMoviesInRatingRange(double lowerBound, double upperBound) {
        return this.moviesById.values()
                .stream()
                .filter(m -> m.getRating() >= lowerBound && m.getRating() <= upperBound)
                .sorted(Comparator.comparing(Movie::getRating).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public Iterable<Movie> getAllMoviesOrderedByActorPopularityThenByRatingThenByYear() {
        return this.moviesById.values()
                .stream()
                .sorted((l,r)->{
                    Integer lTotal = this.getTotalAmountOfMoviesPlayedByActor(l);
                    Integer rTotal = this.getTotalAmountOfMoviesPlayedByActor(r);
                    if (Objects.equals(lTotal, rTotal)) {
                        double lRating = l.getRating();
                        double rRating = r.getRating();
                        if (lRating == rRating) {
                            return r.getReleaseYear() - l.getReleaseYear();
                        }
                        return Double.compare(rRating, lRating);
                    }
                    return Integer.compare(rTotal, lTotal);
                }).collect(Collectors.toList());

    }

    public Integer getTotalAmountOfMoviesPlayedByActor(Movie movie) {
        return movie.getActors()
                .stream()
                .mapToInt(m -> this.moviesCountByActor.get(m))
                .sum();


    }
}
