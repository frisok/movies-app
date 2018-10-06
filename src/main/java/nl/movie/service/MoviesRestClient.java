package nl.movie.service;

import nl.movie.service.domain.Movie;

import java.util.List;

/**
 *
 */
@FunctionalInterface
public interface MoviesRestClient {

    List<Movie> findByCity(String city);

}