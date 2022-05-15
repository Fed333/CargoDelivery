package com.epam.cargo.service;

import com.epam.cargo.custom.IMonthlyProfit;
import com.epam.cargo.repos.DeliveryReceiptRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Class for reporting profit. <br>
 * @author Roman Kovalchuk
 * */
@Service
public class ProfitReporterService {

    private final DeliveryReceiptRepo receiptRepo;

    @Autowired
    public ProfitReporterService(DeliveryReceiptRepo receiptRepo) {
        this.receiptRepo = receiptRepo;
    }

    public List<IMonthlyProfit> monthlyProfit(){
        return receiptRepo.monthlyProfit();
    }
}