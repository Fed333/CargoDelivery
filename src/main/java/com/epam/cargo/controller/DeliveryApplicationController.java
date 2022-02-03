package com.epam.cargo.controller;

import com.epam.cargo.dto.DeliveryApplicationRequest;
import com.epam.cargo.entity.BaggageType;
import com.epam.cargo.entity.DeliveryApplication;
import com.epam.cargo.entity.User;
import com.epam.cargo.exception.WrongDataException;
import com.epam.cargo.service.CityService;
import com.epam.cargo.service.DeliveryApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

@Controller
@RequestMapping("/make_app")
public class DeliveryApplicationController {

    @Autowired
    private DeliveryApplicationService applicationService;

    @Autowired
    public CityService cityService;

    @Value("${spring.messages.basename}")
    private String messages;

    @GetMapping
    public String makeApplicationPage(
            @AuthenticationPrincipal User customer,
            DeliveryApplicationRequest applicationRequest,
            BindingResult bindingResult,
            Model model,
            Locale locale
    ){
        if (!bindingResult.hasErrors()) {

            List<DeliveryApplication> applications = applicationService.findAll();
            applications.forEach(System.out::println);

        }
        else{
            ResourceBundle  bundle = ResourceBundle.getBundle(messages, locale);
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult, bundle);
            model.mergeAttributes(errors);

        }

        model.addAttribute("types", BaggageType.values());
        model.addAttribute("cities", cityService.findAll(locale, Sort.by(Sort.Order.by("name"))));

        return "makeDeliveryApplication";
    }

    @PostMapping
    public String sendApplication(
            @AuthenticationPrincipal User customer,
            @Valid  DeliveryApplicationRequest applicationRequest,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Model model,
            Locale locale
    ){
        boolean successfullySent = false;
        if (!bindingResult.hasErrors()){
            try {
                successfullySent = applicationService.sendApplication(customer, applicationRequest);
                model.addAttribute("result", "application was successfully sent");
            }
            catch (WrongDataException e){
                model.addAttribute(e.getModelAttribute(), e.getMessage());

            }
        }
        else{
            ResourceBundle bundle = ResourceBundle.getBundle(messages, locale);
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult, bundle);
            model.mergeAttributes(errors);
        }
        if (!successfullySent) {
            model.asMap().forEach(redirectAttributes::addFlashAttribute);
            return "redirect:/make_app";
        }
        return "redirect:/";
    }
}
