package com.epam.cargo.repos;

import com.epam.cargo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository of fetching User objects from database.
 * @author Roman Kovalchuk
 * @version 1.0
 * */
public interface UserRepo extends JpaRepository<User, Long> {

    @Override
    Optional<User> findById(Long id);

    @Override
    List<User> findAll();

    User findByLogin(String login);

}
