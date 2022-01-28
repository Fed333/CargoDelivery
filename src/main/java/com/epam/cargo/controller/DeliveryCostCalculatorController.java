package com.epam.cargo.controller;

import com.epam.cargo.dto.DeliveryCostCalculatorRequest;
import com.epam.cargo.dto.DeliveryCostCalculatorResponse;
import com.epam.cargo.entity.City;
import com.epam.cargo.exception.WrongDataException;
import com.epam.cargo.service.CityService;
import com.epam.cargo.service.DeliveryCostCalculatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Locale;

@Controller
@RequestMapping("/delivery_cost_calculator")
public class DeliveryCostCalculatorController {

    @Autowired
    private DeliveryCostCalculatorService deliveryCostCalculatorService;

    @Autowired
    private CityService cityService;

    @GetMapping
    public String calculatorPage(
            Model model,
            City.Distance distance,
            Locale locale
    ){
        model.addAttribute("distance", distance);
        model.addAttribute("cities", cityService.findAll(locale));
        return "deliveryCostCalculator";
    }

    @PostMapping
    public String calculateCost(
            DeliveryCostCalculatorRequest calculatorRequest,
            RedirectAttributes redirectAttributes,
            Locale locale
    ){

        try {
            DeliveryCostCalculatorResponse response = deliveryCostCalculatorService.calculateCost(calculatorRequest, locale);
            redirectAttributes.addFlashAttribute("cost", response.getCost());
            redirectAttributes.addFlashAttribute("distance", response.getDistance());
        }
        catch (WrongDataException e){
            redirectAttributes.addFlashAttribute(e.getModelAttribute(), e.getMessage());
        }
        return "redirect:/delivery_cost_calculator";
    }
}
