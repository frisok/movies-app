package nl.movie.service;

import nl.movie.data.entity.User;

import java.util.List;

/**
 *
 */
public interface UserService {

    List<User> findAll();

}