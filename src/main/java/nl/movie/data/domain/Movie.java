package nl.movie.data.domain;

import lombok.Getter;
import lombok.Setter;
import nl.movie.service.util.MoviesDateUtil;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 */
@Getter
@Setter
public class Movie implements Serializable, Comparable<Movie> {

    private static final long serialVersionUID = -4039587343866359063L;

    private String title;
    private MovieDetails movieDetails;
    private List<Screening> screenings = new ArrayList<>();


    public String screeningsToday() {

        String result = screenings
                .stream()
                .filter(s -> MoviesDateUtil.sameDay(new Date(), s.getStartDateTime()))
                .filter(s -> MoviesDateUtil.inFuture(s.getStartDateTime()))
                .map(s -> MoviesDateUtil.extractTime(s.getStartDateTime()) + " (" + s.getCinema().getName() + "),\n")
                .reduce("", String::concat);

        final int index = result.lastIndexOf(',');
        if (index > 0) {
            result = result.substring(0, index);
        }

        return result;
    }

    @Override
    public int compareTo(final Movie o) {
        if (StringUtils.isBlank(title)) {
            return -1;
        } else if (o == null || StringUtils.isBlank(o.getTitle())) {
            return 1;
        } else {
            return title.compareTo(o.getTitle());
        }
    }
}