package com.epam.cargo.service;

import com.epam.cargo.dto.DeliveryApplicationRequest;
import com.epam.cargo.entity.DeliveredBaggage;
import com.epam.cargo.entity.DeliveryApplication;
import com.epam.cargo.entity.User;
import com.epam.cargo.exception.NoExistingCityException;
import com.epam.cargo.exception.NoExistingDirectionException;
import com.epam.cargo.exception.WrongDataException;
import com.epam.cargo.repos.DeliveryApplicationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

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

    public boolean sendApplication(User customer, DeliveryApplicationRequest request) throws WrongDataException {
        Objects.requireNonNull(customer, "Customer object cannot be null");
        Objects.requireNonNull(request, "DeliveryApplicationRequest object cannot be null");

        DeliveryApplication application = ServiceUtils.createDeliveryApplication(customer, request, cityService);
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
}
