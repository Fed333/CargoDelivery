package com.epam.cargo.service;

import com.epam.cargo.custom.AmountBaggagePerType;
import com.epam.cargo.repos.DeliveredBaggageRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AmountBaggagePerTypeService {

    private final DeliveredBaggageRepo baggageRepo;

    public List<AmountBaggagePerType> getBaggageReport(){
        return baggageRepo.countBaggagePerType();
    }
}
