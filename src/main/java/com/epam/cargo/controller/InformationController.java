package com.epam.cargo.controller;

import com.epam.cargo.service.DirectionDeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InformationController {

    @Autowired
    private DirectionDeliveryService directionDeliveryService;

    @GetMapping("/")
    public String information(Model model){
        model.addAttribute("directions", directionDeliveryService.findAll());
        return "information";
    }
}
