package com.epam.cargo.controller;

import com.epam.cargo.dto.UserRequest;
import com.epam.cargo.entity.City;
import com.epam.cargo.exception.WrongDataException;
import com.epam.cargo.service.CityService;
import com.epam.cargo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
            UserRequest userRequest,
            Model model,

            Locale locale
    ){
        List<City> cities = cityService.findAll(locale, Sort.by(Sort.Order.by("name")));
        model.addAttribute("cities", cities);
        return "registration";
    }

    @PostMapping
    public String registration(
            UserRequest userRequest,
            Locale locale,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        try {
            userService.registerUser(userRequest, locale);
        }
        catch (WrongDataException e) {
            model.addAttribute(e.getModelAttribute(), e.getMessage());
            redirectAttributes.addFlashAttribute("userRequest", userRequest);
            redirectAttributes.addFlashAttribute(e.getModelAttribute(), e.getMessage());
            return "redirect:/registration";
        }

        return "redirect:/login";
    }
}
