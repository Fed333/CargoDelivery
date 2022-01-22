package com.epam.cargo.controller;

import com.epam.cargo.dto.UserRequest;
import com.epam.cargo.entity.City;
import com.epam.cargo.exception.WrongDataException;
import com.epam.cargo.service.CityService;
import com.epam.cargo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

    @Autowired
    private UserService userService;

    @Autowired
    private CityService cityService;

    @GetMapping
    public String registrationPage(
            Model model,
            Locale locale
    ){
        List<City> cities = cityService.findAll(locale);
        model.addAttribute("cities", cities);
        return "registration";
    }

    @PostMapping
    public String registration(
            UserRequest userRequest,
            Locale locale,
            Model model
    ) {
        try {
            userService.registerUser(userRequest, locale);
        }
        catch (WrongDataException e) {
            model.addAttribute(e.getModelAttribute(), e.getMessage());
            return "redirect:/registration";
        }
        return "redirect:/login";
    }
}