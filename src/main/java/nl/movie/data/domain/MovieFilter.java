package nl.movie.data.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * Wrapper class for filtering the list of movies
 */
@Getter
@Setter
@Builder
public class MovieFilter implements Serializable {

    private static final long serialVersionUID = 1219584245010825360L;

    private String title;
    private Date start;
    private Date end;
    private boolean childFriendly;
    private String city;
    private int maxDistanceinKM;

}