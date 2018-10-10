package nl.movie.service;

import nl.movie.data.domain.Movie;

import java.util.List;
import java.util.Optional;

/**
 *
 */
public interface MoviesRestClient {

    List<Movie> findByCity(String city);

    Optional<String> login(String username, String password);

    void updateMovies(String authenticationToken);

}