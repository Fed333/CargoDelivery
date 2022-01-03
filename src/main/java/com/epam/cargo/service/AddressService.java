package com.epam.cargo.service;

import com.epam.cargo.entity.Address;
import com.epam.cargo.repos.AddressRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService {

    @Autowired
    private AddressRepo addressRepo;

    public boolean addAddress(Address address){
        addressRepo.save(address);
        return true;
    }

    public Address findAddressById(Long id){
        return addressRepo.findById(id).orElseThrow();
    }

}
