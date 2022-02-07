package com.epam.cargo.service;

import com.epam.cargo.dto.DeliveryReceiptRequest;
import com.epam.cargo.entity.DeliveryApplication;
import com.epam.cargo.entity.DeliveryReceipt;
import com.epam.cargo.entity.User;
import com.epam.cargo.exception.WrongDataException;
import com.epam.cargo.repos.DeliveryReceiptRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class DeliveryReceiptService {
    @Autowired
    private DeliveryReceiptRepo receiptRepo;

    @Autowired
    private DeliveryApplicationService applicationService;

    public Optional<DeliveryReceipt> findById(Long id){
        return receiptRepo.findById(id);
    }

    public List<DeliveryReceipt> findAll(){
        return receiptRepo.findAll();
    }

    public boolean addReceipt(DeliveryReceipt receipt) throws WrongDataException {
        if (Objects.isNull(receipt) || receiptRepo.findByApplicationId(receipt.getApplication().getId()).isPresent()){
            return false;
        }

        receiptRepo.save(receipt);
        DeliveryApplication application = receipt.getApplication();
        application.setState(DeliveryApplication.State.CONFIRMED);
        applicationService.saveApplication(application);
        return true;
    }

    public boolean makeReceipt(DeliveryApplication application, User manager, DeliveryReceiptRequest receiptRequest) throws WrongDataException {
        Objects.requireNonNull(application, "application cannot be null");
        Objects.requireNonNull(manager, "manager cannot be null");
        Objects.requireNonNull(receiptRequest, "receiptRequest cannot be null");

        if (!isSubmittedState(application)){
            return false;
        }

        DeliveryReceipt receipt = ServiceUtils.createDeliveryReceipt(application, manager, receiptRequest);
        requireValidReceipt(receipt);
        return addReceipt(receipt);
    }

    private boolean isSubmittedState(DeliveryApplication application) {
        return application.getState().equals(DeliveryApplication.State.SUBMITTED);
    }

    private void requireValidReceipt(DeliveryReceipt receipt){
        Objects.requireNonNull(receipt, "Receipt cannot be null");
        Objects.requireNonNull(receipt.getApplication(), "Application cannot be null");
        Objects.requireNonNull(receipt.getCustomer(), "Customer cannot be null");
        Objects.requireNonNull(receipt.getManager(), "Manager cannot be null");
        Objects.requireNonNull(receipt.getFormationDate(), "FormationDate cannot be null");
    }

    public List<DeliveryReceipt> findAllByUserId(Long id) {
        return receiptRepo.findAllByCustomerId(id);
    }
}
