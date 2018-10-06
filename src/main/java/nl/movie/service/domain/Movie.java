package nl.movie.service.domain;

import lombok.Getter;
import lombok.Setter;
import nl.movie.service.util.MoviesDateUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 */
@Getter
@Setter
public class Movie implements Serializable {

    private static final long serialVersionUID = -4039587343866359063L;

    private String title;
    private MovieDetails movieDetails;
    private List<Screening> screenings = new ArrayList<>();


    public String screeningsToday() {

        String result = screenings
                .stream()
                .filter(s -> MoviesDateUtil.sameDay(new Date(), s.getStartDateTime()))
                .map(s -> MoviesDateUtil.extractTime(s.getStartDateTime()) + " (" + s.getCinema().getName() + "),\n")
                .reduce("", String::concat);

        final int index = result.lastIndexOf(',');
        if (index > 0) {
            result = result.substring(0, index);
        }

        return result;
    }

}