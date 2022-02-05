package com.epam.cargo.service;

import com.epam.cargo.dto.DeliveryApplicationRequest;
import com.epam.cargo.dto.DeliveryApplicationsReviewFilterRequest;
import com.epam.cargo.entity.DeliveredBaggage;
import com.epam.cargo.entity.DeliveryApplication;
import com.epam.cargo.entity.User;
import com.epam.cargo.exception.NoExistingCityException;
import com.epam.cargo.exception.NoExistingDirectionException;
import com.epam.cargo.exception.WrongDataException;
import com.epam.cargo.repos.DeliveryApplicationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class DeliveryApplicationService {

    @Autowired
    private DeliveryApplicationRepo deliveryApplicationRepo;

    @Autowired
    private CityService cityService;

    @Autowired
    private DeliveredBaggageService deliveredBaggageService;

    @Autowired
    private UserService userService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private DeliveryCostCalculatorService costCalculatorService;

    public boolean sendApplication(User customer, DeliveryApplicationRequest request, ResourceBundle bundle) throws WrongDataException {
        Objects.requireNonNull(customer, "Customer object cannot be null");
        Objects.requireNonNull(request, "DeliveryApplicationRequest object cannot be null");

        DeliveryApplication application = ServiceUtils.createDeliveryApplication(customer, request, cityService, bundle);
        application.setPrice(calculatePrice(application));
        return sendApplication(application);

    }

    private Double calculatePrice(DeliveryApplication application) throws NoExistingDirectionException {
        Double distanceCost = costCalculatorService.calculateDistanceCost(application.getSenderAddress(), application.getReceiverAddress());
        DeliveredBaggage deliveredBaggage = application.getDeliveredBaggage();
        Double weightCost = costCalculatorService.calculateWeightCost(deliveredBaggage.getWeight());
        Double dimensionsCost = costCalculatorService.calculateDimensionsCost(deliveredBaggage.getVolume());
        return distanceCost + weightCost + dimensionsCost;
    }

    public boolean sendApplication(DeliveryApplication application) throws NoExistingCityException {
        if (Objects.isNull(application)){
            return false;
        }
        ServiceUtils.requireExistingUser(application.getCustomer(), userService);

        deliveredBaggageService.addBaggage(application.getDeliveredBaggage());
        addressService.addAddress(application.getSenderAddress());
        addressService.addAddress(application.getReceiverAddress());
        deliveryApplicationRepo.save(application);

        return true;
    }

    public List<DeliveryApplication> findAll() {
        return deliveryApplicationRepo.findAll();
    }

    public Page<DeliveryApplication> findAllByUserId(Long id, Pageable pageable) {
        return deliveryApplicationRepo.findAllByUserId(id, pageable);
    }

    public List<DeliveryApplication> findAllByUserId(Long id) {
        return deliveryApplicationRepo.findAllByUserId(id);
    }

    public List<DeliveryApplication> findAll(DeliveryApplicationsReviewFilterRequest filter){
        List<DeliveryApplication> applications = findAll();
        applications = applications.stream()
                .filter(
                        statePredicate(filter)
                                .and(baggageTypePredicate(filter))
                                .and(senderCityPredicate(filter))
                                .and(receiverCityPredicate(filter))
                                .and(sendingDatePredicate(filter))
                                .and(receivingDatePredicate(filter))
                )
                .collect(Collectors.toList());

        return applications;
    }

    private Predicate<? super DeliveryApplication> receivingDatePredicate(DeliveryApplicationsReviewFilterRequest filter) {
        return a -> Objects.isNull(filter.getReceivingDate()) || a.getReceivingDate().equals(filter.getReceivingDate());
    }

    private Predicate<? super DeliveryApplication> sendingDatePredicate(DeliveryApplicationsReviewFilterRequest filter) {
        return a -> Objects.isNull(filter.getSendingDate()) || a.getSendingDate().equals(filter.getSendingDate());
    }

    private Predicate<? super DeliveryApplication> senderCityPredicate(DeliveryApplicationsReviewFilterRequest filter) {
        return a -> Objects.isNull(filter.getCityFromId()) || a.getSenderAddress().getCity().getId().equals(filter.getCityFromId());
    }

    private Predicate<? super DeliveryApplication> receiverCityPredicate(DeliveryApplicationsReviewFilterRequest filter) {
        return a -> Objects.isNull(filter.getCityToId()) || a.getReceiverAddress().getCity().getId().equals(filter.getCityToId());
    }

    private Predicate<DeliveryApplication> baggageTypePredicate(DeliveryApplicationsReviewFilterRequest filter) {
        return a -> Objects.isNull(filter.getBaggageType()) || a.getDeliveredBaggage().getType().equals(filter.getBaggageType());
    }


    private Predicate<DeliveryApplication> statePredicate(DeliveryApplicationsReviewFilterRequest filter) {
        return a -> Objects.isNull(filter.getApplicationState()) || a.getState().equals(filter.getApplicationState());
    }

    public Page<DeliveryApplication> getPage(DeliveryApplicationsReviewFilterRequest applicationsRequest, Pageable pageable) {
        List<DeliveryApplication> list = findAll(applicationsRequest);
        return ServiceUtils.toPage(list, pageable, new DeliveryApplicationComparatorRecognizer());
    }

    private static class DeliveryApplicationComparatorRecognizer implements ServiceUtils.ComparatorRecognizer<DeliveryApplication> {

        private final Map<String, Comparator<DeliveryApplication>> comparators;

        DeliveryApplicationComparatorRecognizer(){
            comparators = new HashMap<>();
            comparators.put("id", Comparator.comparing(DeliveryApplication::getId, Long::compareTo));
        }

        @Override
        public Comparator<DeliveryApplication> getComparator(Sort.Order order) {
            Comparator<DeliveryApplication> cmp = comparators.get(order.getProperty());
            if (!Objects.isNull(cmp) && order.isDescending()){
                cmp = cmp.reversed();
            }
            return cmp;
        }
    }
}
