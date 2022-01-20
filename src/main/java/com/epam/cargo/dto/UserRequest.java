package com.epam.cargo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {
    private String name;
    private String surname;

    private String login;
    private String password;
    private String duplicatePassword;

    private String phone;
    private String email;

    private AddressRequest address;

}
