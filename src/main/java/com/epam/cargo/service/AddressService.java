package com.epam.cargo.service;

import com.epam.cargo.entity.Address;
import com.epam.cargo.repos.AddressRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AddressService {

    @Autowired
    private AddressRepo addressRepo;

    public boolean addAddress(Address address){
        Address foundAddress;
        if (!Objects.isNull(foundAddress = addressRepo.findByHouseNumberAndCityAndStreet(address.getHouseNumber(), address.getCity(), address.getStreet()))){
             address.setId(foundAddress.getId());
        }
        addressRepo.save(address);
        return true;
    }

    public void deleteAddress(Address address){
        addressRepo.delete(address);
    }

    public Address findAddressById(Long id){
        return addressRepo.findById(id).orElseThrow();
    }

}
