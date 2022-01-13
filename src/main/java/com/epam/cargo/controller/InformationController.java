package com.epam.cargo.controller;

import com.epam.cargo.entity.DirectionDelivery;
import com.epam.cargo.service.DirectionDeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Locale;

@Controller
public class InformationController {

    @Autowired
    private DirectionDeliveryService directionDeliveryService;

    @GetMapping("/")
    public String information(
            Model model,
            @PageableDefault(sort = {"senderCity.name"}, direction = Sort.Direction.ASC) Pageable pageable,
            Locale locale
    ){
        Page<DirectionDelivery> directionsPage;

        directionsPage = directionDeliveryService.findAll(pageable, locale);

        model.addAttribute("directionsPage", directionsPage);
        model.addAttribute("url", "/");

        return "information";
    }
}
