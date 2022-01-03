package com.epam.cargo.service;

import com.epam.cargo.entity.Role;
import com.epam.cargo.entity.User;
import com.epam.cargo.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    public boolean addUser(User user){
        if (userRepo.findByLogin(user.getLogin()) != null){
            return false;
        }
        user.setRoles(Collections.singleton(Role.USER));
        userRepo.save(user);

        return true;
    }

    public User findUserById(Long id){
        return userRepo.findById(id).orElseThrow();
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userRepo.findByLogin(s);
    }
}
