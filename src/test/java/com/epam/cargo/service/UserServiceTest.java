package com.epam.cargo.service;

import com.epam.cargo.entity.Address;
import com.epam.cargo.entity.User;
import org.hibernate.PropertyValueException;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;


@RunWith(SpringRunner.class)
@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private CityService cityService;

    @Test
    void addUser() {
        User user = new User();
        String name = "Roman";
        String login = "romanko_03";
        String password = "1234";

        user.setName(name);
        user.setLogin(login);
        user.setPassword(password);

        userService.addUser(user);

        Assert.assertEquals(password, userService.loadUserByUsername(login).getPassword());
        Assert.assertEquals(user.getId(), userService.findUserByLogin(login).getId());
    }

    @Test
    void addUserAndDeleteThen(){
        User user = new User();
        String name = "User";
        String login = "admin";
        String password = "12345";

        user.setName(name);
        user.setLogin(login);
        user.setPassword(password);

        userService.addUser(user);

        Assert.assertEquals(password, userService.loadUserByUsername(login).getPassword());
        Assert.assertEquals(user.getId(), userService.findUserByLogin(login).getId());

        userService.deleteUser(user);

        Assert.assertTrue(Objects.isNull(userService.findUserByLogin(login)));

    }

    @Test
    void addUserFailed(){
        User user = new User();
        Assert.assertThrows(DataIntegrityViolationException.class, ()->userService.addUser(user));
    }

    @Test
    void addUserWithAddress(){
        String zipcodeOfVinnitsia = "21012";
        Address address = new Address(cityService.findCityByZipCode(zipcodeOfVinnitsia), "Soborna", "68");

        String name = "Ivan";
        String login = "divan0_0";
        String password = "123";

        User user = new User(name, login, password);
        user.setAddress(address);

        userService.addUser(user);

        Assert.assertEquals(password, userService.loadUserByUsername(login).getPassword());
        Assert.assertEquals(user.getId(), userService.findUserByLogin(login).getId());

        Assert.assertFalse(Objects.isNull(userService.findUserByLogin(login).getAddress()));
    }
}