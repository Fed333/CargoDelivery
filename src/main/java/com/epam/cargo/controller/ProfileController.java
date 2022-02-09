package com.epam.cargo.controller;

import com.epam.cargo.entity.DeliveryApplication;
import com.epam.cargo.entity.DeliveryReceipt;
import com.epam.cargo.entity.User;
import com.epam.cargo.service.DeliveryApplicationService;
import com.epam.cargo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Locale;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private UserService userService;

    @Autowired
    private DeliveryApplicationService applicationService;

    @GetMapping
    public String authorizedUserProfilePage(
            @AuthenticationPrincipal User user,
            @Qualifier("applications")
            @PageableDefault(size = 7, sort = {"id"}, direction = Sort.Direction.DESC) Pageable applicationsPageable,
            @Qualifier("receipts")
            @PageableDefault(size = 5, sort = {"id"}, direction = Sort.Direction.DESC) Pageable receiptsPageable,
            @RequestParam(name = "activePill", defaultValue = "pills-applications-tab", required = false) String activePill,
            Model model,
            Locale locale
    ){

        Page<DeliveryApplication> applications = userService.getApplications(user, applicationsPageable);
        Page<DeliveryReceipt> receipts = userService.getCustomerReceipts(user, receiptsPageable);
        model.addAttribute("receipts", receipts);
        model.addAttribute("applications", applications);
        model.addAttribute("user", user);
        model.addAttribute("url", "/profile");
        model.addAttribute("activePill", activePill);
        model.addAttribute("lang", locale.getLanguage());


        return "profile";
    }

    @PreAuthorize("hasAuthority('MANAGER')")
    @GetMapping("{customer}")
    public String profilePage(
            @PathVariable User customer,
            @Qualifier("applications")
            @PageableDefault(size = 7, sort = {"id"}, direction = Sort.Direction.DESC) Pageable applicationsPageable,
            @Qualifier("receipts")
            @PageableDefault(size = 5, sort = {"id"}, direction = Sort.Direction.DESC) Pageable receiptsPageable,
            @RequestParam(name = "activePill", defaultValue = "pills-applications-tab", required = false) String activePill,
            Model model,
            Locale locale
    ){
        Page<DeliveryApplication> applications = userService.getApplications(customer, applicationsPageable);
        Page<DeliveryReceipt> receipts = userService.getCustomerReceipts(customer, receiptsPageable);
        model.addAttribute("applications", applications);
        model.addAttribute("receipts", receipts);
        model.addAttribute("customer", customer);
        model.addAttribute("url", String.format("/profile/%s", customer.getId()));
        model.addAttribute("activePill", activePill);
        model.addAttribute("lang", locale.getLanguage());

        return "profileReview";
    }

}
