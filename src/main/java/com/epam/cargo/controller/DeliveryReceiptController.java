package com.epam.cargo.controller;

import com.epam.cargo.dto.DeliveryReceiptRequest;
import com.epam.cargo.entity.DeliveryApplication;
import com.epam.cargo.entity.DeliveryReceipt;
import com.epam.cargo.entity.User;
import com.epam.cargo.exception.WrongDataException;
import com.epam.cargo.service.DeliveryReceiptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.*;

@Controller
public class DeliveryReceiptController {

    @Autowired
    private DeliveryReceiptService receiptService;

    @Value("${spring.messages.basename}")
    private String messages;

    @PreAuthorize("hasAuthority('MANAGER')")
    @GetMapping("/application/{application}/accept")
    public String acceptApplicationPage(
            @PathVariable DeliveryApplication application,
            @AuthenticationPrincipal User manager,
            @RequestParam(name = "price", required = false) Double price,
            Model model
    ){
        if (Objects.isNull(price)){
            price = application.getPrice();
        }

        List<DeliveryReceipt> receipts = receiptService.findAll();

        model.addAttribute("price", price);
        model.addAttribute("url", String.format("/application/%s/accept", application.getId()));
        model.addAttribute("application", application);
        model.addAttribute("customer", application.getCustomer());
        model.addAttribute("manager", manager);
        return "deliveryReceipt";
    }

    @PreAuthorize("hasAuthority('MANAGER')")
    @PostMapping("/application/{application}/accept")
    public String makeReceipt(
            @PathVariable DeliveryApplication application,
            @AuthenticationPrincipal User manager,
            @Valid DeliveryReceiptRequest receiptRequest,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Model model,
            Locale locale
    ){
        if(!application.getState().equals(DeliveryApplication.State.SUBMITTED)){
            redirectAttributes.addFlashAttribute("application", application);
            return "redirect:/application/confirmation/failed";
        }
        String url = String.format("/application/%s/accept", application.getId());

        boolean successfullyMade = false;
        if(!bindingResult.hasErrors()){
            try {
                successfullyMade = receiptService.makeReceipt(application, manager, receiptRequest);
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

        if (!successfullyMade){
            model.asMap().forEach(redirectAttributes::addFlashAttribute);
            return String.format("redirect:%s", url);
        }

        return "redirect:/applications/review";

    }

    @PreAuthorize("hasAuthority('MANAGER')")
    @GetMapping("/application/confirmation/failed")
    public String failedApplicationConfirmation(
            Model model
    ){
        return "deliveryApplicationConfirmationFailed";
    }
}
