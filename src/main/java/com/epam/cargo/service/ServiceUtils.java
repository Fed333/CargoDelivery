package com.epam.cargo.service;

import com.epam.cargo.dto.AddressRequest;
import com.epam.cargo.dto.DeliveredBaggageRequest;
import com.epam.cargo.dto.DeliveryApplicationRequest;
import com.epam.cargo.dto.DeliveryReceiptRequest;
import com.epam.cargo.entity.*;
import com.epam.cargo.exception.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Utils service class.<br>
 * Has common useful for services static methods.
 * @author Roman Kovalchuk
 * @version 1.0
 * */
public class ServiceUtils {

    static DeliveredBaggage createDeliveredBaggage(DeliveredBaggageRequest request){
        Objects.requireNonNull(request);

        DeliveredBaggage object = new DeliveredBaggage();

        object.setDescription(request.getDescription());
        object.setType(Objects.requireNonNull(request.getType()));
        object.setVolume(Objects.requireNonNull(request.getVolume()));
        object.setWeight(Objects.requireNonNull(request.getWeight()));

        return object;
    }

    /**
     * Makes an object according to dto request object
     * @param customer owner of application
     * @param request web dto request object
     * @param cityService service of City entity
     * @return the object with initialized fields
     * @throws NoExistingCityException if any given city isn't present in the database
     * @throws NullPointerException if any argument is null
     * */
    static DeliveryApplication createDeliveryApplication(User customer, DeliveryApplicationRequest request, CityService cityService, ResourceBundle bundle) throws WrongDataException {
        Objects.requireNonNull(request);
        Objects.requireNonNull(cityService);

        DeliveryApplication object = new DeliveryApplication();

        object.setCustomer(Objects.requireNonNull(customer));

        object.setDeliveredBaggage(createDeliveredBaggage(request.getDeliveredBaggageRequest()));

        Address senderAddress = createAddress(request.getSenderAddress(), cityService);
        Address receiverAddress = createAddress(request.getReceiverAddress(), cityService);

        requireDifferentAddresses(senderAddress, receiverAddress);

        object.setSenderAddress(senderAddress);
        object.setReceiverAddress(receiverAddress);

        object.setSendingDate(Objects.requireNonNull(request.getSendingDate()));
        object.setReceivingDate(Objects.requireNonNull(request.getReceivingDate()));

        requireValidDates(object, bundle);

        object.setState(DeliveryApplication.State.SUBMITTED);
        return object;
    }

    /**
     * Checks whether delivery application's sending and receiving data are valid.<br>
     * Declares that receiving date cannot precede a sending one.<br>
     * @throws WrongDataAttributeException if receiving date precedes sending
     * @since 1.0
     * @author Roman Kovalchuk
     * */
    private static void requireValidDates(DeliveryApplication object, ResourceBundle bundle) throws WrongDataAttributeException {
        if (object.getSendingDate().isAfter(object.getReceivingDate())){
            throw new WrongDataAttributeException(ModelErrorAttribute.RECEIVING.getAttr(), bundle, WrongInput.REQUIRED_BEING_AFTER_SENDING);
        }
    }

    /**
     * Checks whether addresses are different.<br>
     * Declares that sender and receiver address cannot be equal.<br>
     * @throws IllegalArgumentException if sender and receiver addresses are the same
     * @since 1.0
     * @author Roman Kovalchuk
     * */
    private static void requireDifferentAddresses(Address senderAddress, Address receiverAddress) {
        if (senderAddress.equals(receiverAddress)){
            throw new IllegalArgumentException("Sender and Receiver address mustn't be equal!");
        }
    }

    static Address createAddress(AddressRequest request, CityService cityService) throws NoExistingCityException {
        Objects.requireNonNull(request);

        Address object = new Address();
        object.setCity(requireExistingCity(request, cityService));
        object.setStreet(request.getStreetName());
        object.setHouseNumber(request.getHouseNumber());

        return object;
    }

    private static City requireExistingCity(AddressRequest request, CityService cityService) throws NoExistingCityException {
        City city = cityService.findCityById(request.getCityId());
        if (Objects.isNull(city)){
            throw new NoExistingCityException();
        }
        return city;
    }

    static void requireExistingUser(User user, UserService userService){
        Optional.ofNullable(user).orElseThrow(()->new IllegalArgumentException("User cannot be null!"));
        if (Objects.isNull(userService.findUserByLogin(user.getLogin()))){
            throw new IllegalArgumentException("User " + user + " must be present in database");
        }
    }

    static <T> Page<T> toPage(List<T> list, Pageable pageable, ComparatorRecognizer<T> recognizer) {
        if(pageable.getPageNumber()*pageable.getPageSize() > list.size()){
            pageable = pageable.withPage(0);
        }
        Sort sort = pageable.getSort();
        sortList(list, sort, recognizer);

        int start = (int)pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), list.size());
        if (start > end){
            return new PageImpl<>(Collections.emptyList(), pageable, list.size());
        }
        return new PageImpl<>(list.subList(start, end), pageable, list.size());
    }

    /**
     * Common list sorting with SortRequest and ComparatorRecognizer.<br>
     * @param list List to sort
     * @param sort data of sorting by
     * @param recognizer mean of recognizing according to the SortRequest comparators
     * @since 1.0
     * @see Sort
     * @see ComparatorRecognizer
     * @author Roman Kovalhchuk
     * */
    static <T> void sortList(List<T> list, Sort sort, ComparatorRecognizer<T> recognizer) {

        List<Sort.Order> orders = sort.get().collect(Collectors.toList());

        Comparator<T> comparator = null;

        for (Sort.Order order:orders) {
            if (Objects.isNull(comparator)){
                comparator = recognizer.getComparator(order);
            }
            else{
                comparator = comparator.thenComparing(recognizer.getComparator(order));
            }
        }

        if (!Objects.isNull(comparator)) {
            list.sort(comparator);
        }
    }

    public static DeliveryReceipt createDeliveryReceipt(DeliveryApplication application, User manager, DeliveryReceiptRequest receiptRequest) {
        DeliveryReceipt receipt = new DeliveryReceipt();
        receipt.setApplication(application);
        receipt.setCustomer(application.getCustomer());
        receipt.setManager(manager);
        receipt.setTotalPrice(Optional.ofNullable(receiptRequest.getPrice()).orElse(application.getPrice()));
        receipt.setFormationDate(LocalDate.now());
        receipt.setPaid(false);
        return receipt;
    }

    /**
     * Checks authorized rights for personal actions.
     * @param user specified user for comparison
     * @param initiator user from context who initiated the action
     * @return equals of login and password
     * @since 1.0
     * @author Roman Kovalchuk
     * */
    static boolean credentialsEquals(User user, User initiator) {
        return Objects.equals(user.getLogin(), initiator.getLogin()) && Objects.equals(user.getPassword(), initiator.getPassword());
    }

    /**
     * Interface of further generic implementation for each entity class to be sorted.
     * @author Roman Kovalchuk
     * @version 1.0
     * */
    public interface ComparatorRecognizer<T> {
        Comparator<T> getComparator(Sort.Order order);
    }
}
