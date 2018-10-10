package nl.movie.data.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 *
 */
@Getter
@Setter
public class Credentials implements Serializable {

    private static final long serialVersionUID = 7608227456070543393L;

    private String username;
    private String password;

}

