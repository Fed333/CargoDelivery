package com.epam.cargo.controller;

import com.epam.cargo.dto.DeliveryCostCalculatorRequest;
import com.epam.cargo.dto.DeliveryCostCalculatorResponse;
import com.epam.cargo.exception.WrongDataException;
import com.epam.cargo.service.CityService;
import com.epam.cargo.service.DeliveryCostCalculatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

@Controller
@RequestMapping("/delivery_cost_calculator")
public class DeliveryCostCalculatorController {

    @Autowired
    private DeliveryCostCalculatorService deliveryCostCalculatorService;

    @Autowired
    private CityService cityService;

    @Value("${spring.messages.basename}")
    private String messages;

    @GetMapping
    public String calculatorPage(
            @ModelAttribute(name = "calculatorRequest") @Valid  DeliveryCostCalculatorRequest calculatorRequest,
            BindingResult bindingResult,
            Model model,
            Locale locale
    ){
        if (bindingResult.hasErrors()){
            ResourceBundle bundle = ResourceBundle.getBundle(messages, locale);
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult, bundle);
            model.mergeAttributes(errors);
        }
        model.addAttribute("cities", cityService.findAll(locale));
        return "deliveryCostCalculator";
    }

    @PostMapping
    public String calculateCost(
            @Valid DeliveryCostCalculatorRequest calculatorRequest,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes,
            Locale locale
    ){
        if (!bindingResult.hasErrors()) {
            try {
                DeliveryCostCalculatorResponse response = deliveryCostCalculatorService.calculateCost(calculatorRequest, locale);
                redirectAttributes.addFlashAttribute("cost", response.getCost());
                redirectAttributes.addFlashAttribute("distance", response.getDistance());
            } catch (WrongDataException e) {
                redirectAttributes.addFlashAttribute(e.getModelAttribute(), e.getMessage());
            }
        }
        else{
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult, ResourceBundle.getBundle(messages, locale));
            errors.forEach(redirectAttributes::addFlashAttribute);
        }
        redirectAttributes.addFlashAttribute("calculatorRequest", calculatorRequest);
        return "redirect:/delivery_cost_calculator";
    }
}
