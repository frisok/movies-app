package nl.movie.service.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 *
 */
@Getter
@Setter
public class Screening implements Serializable {

    private static final long serialVersionUID = 3474111379560016885L;

    private Cinema cinema;
    private String startDateTime;

}