package nl.movie.service.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import nl.movie.data.domain.Movie;
import nl.movie.data.domain.MovieFilter;
import nl.movie.service.MoviesRestClient;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 *
 */
@Slf4j
@Component
public class MoviesRestClientImpl implements MoviesRestClient {

    @Value("movies.rest.url:http://localhost:8080/movies")
    private String moviesRestUrl;


    @Override
    public List<Movie> findByFilter(final MovieFilter movieFilter) {

        List<Movie> movies = new ArrayList<>();

        final ResponseEntity<String> response = callRestForMoviesByCity(movieFilter.getCity());

        if (response.getStatusCode().is2xxSuccessful()) {
            movies = new Gson().fromJson(response.getBody(), new TypeToken<List<Movie>>() {
            }.getType());
        }

        return filter(movieFilter, movies);
    }

    private ResponseEntity<String> callRestForMoviesByCity(final String city) {

        ResponseEntity<String> response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

        try {
            if (StringUtils.isBlank(city
            ) || StringUtils.equalsAnyIgnoreCase(city, "all")) {
                response = new RestTemplate().getForEntity("http://localhost:8080/movies", String.class);
            } else {
                response = new RestTemplate().getForEntity("http://localhost:8080/movies/{city}", String.class, city);
            }
        } catch (final Exception e) {
            log.warn("Error when calling " + moviesRestUrl, e);
        }


        return response;
    }

    private List<Movie> filter(MovieFilter movieFilter, List<Movie> movies) {
        return movies.stream()
                .filter(m -> StringUtils.isNotBlank(movieFilter.getTitle()) ? StringUtils.containsIgnoreCase(m.getTitle(), movieFilter.getTitle()) : true)
                .filter(m -> movieFilter.isChildFriendly() ? StringUtils.equalsAnyIgnoreCase(m.getMovieDetails().getRated(), "G") || StringUtils.equalsAnyIgnoreCase(m.getMovieDetails().getRated(), "PG") : true)
                .collect(Collectors.toList());
    }

    /**
     * https://stackoverflow.com/questions/21920268/basic-authentication-for-rest-api-using-spring-resttemplate
     *
     * @param username
     * @param password
     * @return
     */
    @Override
    public Optional<String> login(String username, String password) {

        ResponseEntity<String> response = ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        try {

            final MultiValueMap<String, String> postParameters = new LinkedMultiValueMap<>();
            postParameters.add("username", username);
            postParameters.add("password", password);
            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(postParameters, new HttpHeaders());
            response = new RestTemplate().postForEntity("http://localhost:8080/login", request, String.class);

        } catch (final Exception e) {
            log.warn("Error when calling http://localhost:8080/login", e);
        }

        return Optional.ofNullable(response.getStatusCode().is2xxSuccessful() ? response.getBody() : null);

    }

    @Override
    public void updateMovies(final String authenticationToken) {

        try {

            final MultiValueMap<String, String> postParameters = new LinkedMultiValueMap<>();
            final HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.AUTHORIZATION, authenticationToken);
            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(postParameters, headers);
            new RestTemplate().postForEntity("http://localhost:8080/updatemovies", request, String.class);

        } catch (final Exception e) {
            log.warn("Error when calling http://localhost:8080/updatemovies", e);
        }
    }

}