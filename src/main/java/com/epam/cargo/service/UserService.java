package com.epam.cargo.service;

import com.epam.cargo.dto.AddressRequest;
import com.epam.cargo.dto.UserRequest;
import com.epam.cargo.entity.*;
import com.epam.cargo.exception.*;
import com.epam.cargo.repos.UserRepo;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService implements UserDetailsService {

    @Value("${validation.password.regexp}")
    private String passwordValidRegex;

    @Value("${validation.login.regexp}")
    private String loginValidRegex;

    @Value("${spring.messages.basename}")
    private String messages;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private AddressService addressService;

    @Autowired
    private CityService cityService;

    @Autowired
    private DeliveryApplicationService applicationService;

    @Autowired
    private DeliveryReceiptService receiptService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public User registerUser(UserRequest userRequest, Locale locale) throws WrongDataException {
        ResourceBundle bundle = ResourceBundle.getBundle(messages, locale);
        User user = new User();
        initializePersonalData(userRequest, user);
        initializeCredentials(userRequest, user, bundle);

        this.addUser(user);
        return user;
    }

    private void initializeCredentials(UserRequest userRequest, User user, ResourceBundle bundle) throws WrongDataException {
        String login = userRequest.getLogin();
        requireValidLogin(login, bundle);
        requireUniqueLogin(login, bundle);
        user.setLogin(login);

        String password = userRequest.getPassword();
        requireValidPassword(password, bundle);
        requirePasswordDuplicationMatch(userRequest, password, bundle);
        user.setPassword(passwordEncoder.encode(password));
    }

    private void requirePasswordDuplicationMatch(UserRequest userRequest, String password, ResourceBundle bundle) throws PasswordConfirmationException {
        if (!password.equals(userRequest.getDuplicatePassword())){
            throw new PasswordConfirmationException(bundle);
        }
    }

    private void requireValidPassword(String password, ResourceBundle bundle) throws NoValidPasswordException {
        if (!isValidPassword(password)){
            throw new NoValidPasswordException(bundle, password);
        }
    }

    private boolean isValidPassword(String password) {
        return password.matches(passwordValidRegex);
    }

    private boolean isValidLogin(String login){
        return login.matches(loginValidRegex);
    }

    private void requireValidLogin(String login, ResourceBundle bundle) throws WrongDataException{
        if (!isValidLogin(login)){
            throw new NoValidLoginException(bundle, login);
        }
    }

    private void requireUniqueLogin(String login, ResourceBundle bundle) throws OccupiedLoginException {
        if (!Objects.isNull(userRepo.findByLogin(login))){
            throw new OccupiedLoginException(bundle, login);
        }
    }

    private void initializePersonalData(UserRequest userRequest, User user) throws WrongDataException{
        user.setName(userRequest.getName());
        user.setSurname(userRequest.getSurname());
        user.setEmail(userRequest.getEmail());
        user.setPhone(userRequest.getPhone());

        assignAddressToUser(userRequest, user);
    }

    private boolean assignAddressToUser(UserRequest userRequest, User user) throws NoExistingCityException {
        AddressRequest addressRequest = userRequest.getAddress();
        if (!Objects.isNull(addressRequest)) {

            City city = cityService.findCityById(addressRequest.getCityId());
            if (!Objects.isNull(city)){
                Address address = new Address();
                address.setCity(city);
                address.setStreet(addressRequest.getStreetName());
                address.setHouseNumber(addressRequest.getHouseNumber());

                addressService.addAddress(address);
                user.setAddress(address);
                return true;
            }
        }
        return false;
    }

    public boolean addUser(User user) throws NoExistingCityException {
        if (Objects.isNull(user)){
            return false;
        }
        User foundUser;
        if (!Objects.isNull(foundUser = userRepo.findByLogin(user.getLogin()))){
            user.setId(foundUser.getId());
            return false;
        }
        user.setRoles(Collections.singleton(Role.USER));

        Address address = user.getAddress();
        if (!Objects.isNull(address)) {
            addressService.addAddress(address);
        }

        userRepo.save(user);

        return true;
    }

    public User findUserById(Long id){
        return userRepo.findById(id).orElse(null);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userRepo.findByLogin(s);
    }


    public User findUserByLogin(String login) {
        return userRepo.findByLogin(login);
    }

    public void deleteUser(User user){
        userRepo.delete(user);
        Optional.ofNullable(user.getAddress()).ifPresent(addressService::deleteAddress);
    }

    public List<DeliveryApplication> getApplications(User user){
        if(!Hibernate.isInitialized(user.getApplications())){
            user.setApplications(applicationService.findAllByUserId(user.getId()));
        }
        return user.getApplications();
    }

    public List<DeliveryReceipt> getReceipt(User user){
        if(!Hibernate.isInitialized(user.getReceipts())){
            user.setReceipts(receiptService.findAllByUserId(user.getId()));
        }
        return user.getReceipts();
    }

    public Page<DeliveryApplication> getApplications(User user, Pageable pageable) {
        return applicationService.findAllByUserId(user.getId(), pageable);
    }
}
