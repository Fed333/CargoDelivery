package com.epam.cargo.controller;

import com.epam.cargo.service.AmountBaggagePerTypeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class BaggageReviewController {

    private final AmountBaggagePerTypeService baggagePerTypeService;
    private final String BAGGAGE_PER_TYPE_REPORT = "baggagePerTypeReport";

    @GetMapping("/baggage/review")
    public String baggageReviewPage(
            Model model
    ) {
        model.addAttribute(BAGGAGE_PER_TYPE_REPORT, baggagePerTypeService.getBaggageReport());
        return "baggageReview";
    }

}
