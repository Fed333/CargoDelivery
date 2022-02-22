package com.epam.cargo.exception;

import java.util.ResourceBundle;

public class InvalidReceivingDateException extends WrongDataAttributeException{

    public InvalidReceivingDateException(ResourceBundle bundle) {
        super("receivingDateErrorMessage", bundle, WrongInput.REQUIRED_BEING_AFTER_SENDING);
    }
}
