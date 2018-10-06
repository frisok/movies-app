package nl.movie.service.domain;

import nl.movie.service.util.MoviesDateUtil;
import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;

/**
 *
 */
public class MovieTest {


    @Test
    public void shouldRetrunScreeningsToday() throws Exception {

        final Movie movie = new Movie();
        final List<Screening> screenings = new ArrayList<>();
        screenings.add(createScreening("cinema 1", new Date()));
        screenings.add(createScreening("cinema 2", new Date()));
        screenings.add(createScreening("cinema 3", new SimpleDateFormat(MoviesDateUtil.DD_MM_YYYY).parse("01-01-2000")));
        movie.setScreenings(screenings);

        final String screeningsToday = movie.screeningsToday();

        Assert.assertThat(screeningsToday.contains("(cinema 1)"), Is.is(equalTo(true)));
        Assert.assertThat(screeningsToday.contains("(cinema 2)"), Is.is(equalTo(true)));
        Assert.assertThat(screeningsToday.contains("(cinema 3)"), Is.is(equalTo(false)));
        Assert.assertThat(screeningsToday.lastIndexOf(',') < (screeningsToday.length() - ",\n".length()), Is.is(true));
    }

    private Screening createScreening(final String cinemaName, final Date date) {

        final Screening screening = new Screening();

        final Cinema cinema = new Cinema();
        cinema.setName(cinemaName);
        screening.setCinema(cinema);
        screening.setStartDateTime(new SimpleDateFormat(MoviesDateUtil.YYYY_MM_DD_T_HH_MM_SS).format(date));

        return screening;
    }

}