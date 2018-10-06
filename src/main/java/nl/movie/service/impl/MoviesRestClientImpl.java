package nl.movie.service.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import nl.movie.service.MoviesRestClient;
import nl.movie.service.domain.Movie;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@Slf4j
@Component
public class MoviesRestClientImpl implements MoviesRestClient {

    @Value("movies.rest.url:http://localhost:8080/movies")
    private String moviesRestUrl;


    @Override
    public List<Movie> findByCity(final String city) {

        List<Movie> movies = new ArrayList<>();

        final ResponseEntity<String> response = callRest(city);

        if (response.getStatusCode().is2xxSuccessful()) {
            movies = new Gson().fromJson(response.getBody(), new TypeToken<List<Movie>>() {
            }.getType());
        }

        return movies;
    }

    private ResponseEntity<String> callRest(final String city) {

        ResponseEntity<String> response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

        try {
            if (StringUtils.equalsAnyIgnoreCase(city, "all")) {
                response = new RestTemplate().getForEntity("http://localhost:8080/movies", String.class);
            } else {
                response = new RestTemplate().getForEntity("http://localhost:8080/movies/{city}", String.class, city);
            }
        } catch (final Exception e) {
            log.warn("Error when calling " + moviesRestUrl, e);
        }


        return response;
    }

}