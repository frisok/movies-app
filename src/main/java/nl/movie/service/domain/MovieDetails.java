package nl.movie.service.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Class that reprsesent Imdb movie details
 */
@Getter
@Setter
public class MovieDetails implements Serializable {

    private static final long serialVersionUID = 2968272191032014605L;

    private String title;

    private String year;

    //See also https://en.wikipedia.org/wiki/Motion_Picture_Association_of_America_film_rating_system
    private String rated;

    private String runtime;

    private String plot;

    private String poster;

    private String actors;

    private String director;

}