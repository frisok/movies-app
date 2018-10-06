package nl.movie.service.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 *
 */
@Getter
@Setter
public class Cinema implements Serializable {

    private static final long serialVersionUID = -6749400629801914431L;

    private String name;
    private String city;

}