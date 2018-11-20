package nl.movie.service;

import nl.movie.data.domain.Movie;
import nl.movie.data.domain.MovieFilter;

import java.util.List;
import java.util.Optional;

/**
 *
 */
public interface MoviesRestClient {

    List<Movie> findByFilter(MovieFilter movieFilter);

    Optional<String> login(String username, String password);

    void updateMovies(String authenticationToken);

}