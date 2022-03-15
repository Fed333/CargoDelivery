package com.epam.cargo.service;

import com.epam.cargo.dto.DeliveryReceiptRequest;
import com.epam.cargo.entity.Address;
import com.epam.cargo.entity.DeliveryApplication;
import com.epam.cargo.entity.DeliveryReceipt;
import com.epam.cargo.entity.User;
import com.epam.cargo.exception.NotEnoughMoneyException;
import com.epam.cargo.exception.WrongDataException;
import com.epam.cargo.repos.DeliveryReceiptRepo;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * Service class for managing DeliveryReceipt objects.<br>
 * @author Roman Kovalchuk
 * @see DeliveryReceipt
 * @version 1.0
 * */
@Service
public class DeliveryReceiptService {

    private static final Logger logger = Logger.getLogger(DeliveryReceiptService.class);

    @Autowired
    private DeliveryReceiptRepo receiptRepo;

    @Autowired
    private DeliveryApplicationService applicationService;

    @Autowired
    private UserService userService;

    @Value("${spring.messages.basename}")
    private String messages;

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

        printMadeReceiptSuccessfullyLog(receipt, application);
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

    public List<DeliveryReceipt> findAllByCustomerId(Long id) {
        return receiptRepo.findAllByCustomerId(id);
    }

    /**
     * Makes a payment of delivery receipt by corresponding user
     * @param receipt delivery receipt to pay
     * @param initiator a person who initiated the payment
     * */
    public void payReceipt(DeliveryReceipt receipt, User initiator) throws NotEnoughMoneyException {
        requireValidReceipt(receipt);
        User customer = receipt.getCustomer();
        if(!ServiceUtils.credentialsEquals(customer, initiator)){
            throw new IllegalStateException("You don't have access for this page");
        }
        if (receipt.getPaid()){
            throw new IllegalStateException("Receipt's been already paid");
        }
        BigDecimal balance = initiator.getCash().subtract(BigDecimal.valueOf(receipt.getTotalPrice()));

        if (balance.compareTo(BigDecimal.ZERO) < 0){
            Locale locale = LocaleContextHolder.getLocale();
            ResourceBundle bundle = ResourceBundle.getBundle(messages, locale);
            throw new NotEnoughMoneyException(initiator.getCash(), receipt, bundle);
        }

        initiator.setCash(balance);
        receipt.setCustomer(initiator);
        receipt.setPaid(Boolean.TRUE);

        if(Hibernate.isInitialized(initiator.getReceipts())){
            List<DeliveryReceipt> receipts = initiator.getReceipts();
            receipts.forEach(r ->{
                if(Objects.equals(r.getId(), receipt.getId())){
                    r.setPaid(Boolean.TRUE);
                }
            });
        }

        userService.saveUser(initiator);
        receiptRepo.save(receipt);
    }

    public Page<DeliveryReceipt> findAllByCustomerId(Long id, Pageable pageable) {
        return receiptRepo.findAllByCustomerId(id, pageable);
    }

    public Optional<DeliveryReceipt> findByApplicationId(Long id) {
        return receiptRepo.findByApplicationId(id);
    }

    public void deleteById(Long id) {
        receiptRepo.deleteById(id);
    }

    private void printMadeReceiptSuccessfullyLog(DeliveryReceipt receipt, DeliveryApplication application) {
        User customer = application.getCustomer();
        String customerFullName = String.format("%s %s", customer.getName(), customer.getSurname());
        User manager = receipt.getManager();
        String managerFullName = String.format("%s %s", manager.getName(), manager.getSurname());
        logger.info(
                String.format("Receipt: [id=%1$d, application_id=%2$d, customer: [id=%3$d, name=%4$s], manager: [id=%5$d, name=%6$s], price=%7$f] has been made successfully.",
                        receipt.getId(), application.getId(), customer.getId(), customerFullName, manager.getId(), managerFullName, receipt.getTotalPrice()
                )
        );
    }

}
