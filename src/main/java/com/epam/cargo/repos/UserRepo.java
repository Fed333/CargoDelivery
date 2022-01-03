package com.epam.cargo.repos;

import com.epam.cargo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {

    @Override
    Optional<User> findById(Long id);

    @Override
    List<User> findAll();

    User findByLogin(String login);
}
