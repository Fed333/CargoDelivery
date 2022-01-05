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
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private AddressService addressService;

    public boolean addUser(User user){
        if (userRepo.findByLogin(user.getLogin()) != null){
            return false;
        }
        user.setRoles(Collections.singleton(Role.USER));
        Optional.ofNullable(user.getAddress()).ifPresent(addressService::addAddress);
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


    public User findUserByLogin(String login) {
        return userRepo.findByLogin(login);
    }

    public void deleteUser(User user){
        userRepo.delete(user);
    }
}
