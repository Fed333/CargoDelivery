package com.epam.cargo.exception;

import java.util.ResourceBundle;

import static com.epam.cargo.exception.WrongInput.CONFIRMATION_PASSWORD_FAILED;

public class PasswordConfirmationException extends WrongDataException {

    public PasswordConfirmationException(ResourceBundle bundle) {
        super(bundle.getString(CONFIRMATION_PASSWORD_FAILED));
    }

    public String getModelAttribute() {
        return "duplicatePasswordErrorMessage";
    }
}
