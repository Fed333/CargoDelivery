package com.epam.cargo.exception;

import java.util.ResourceBundle;

public class DuplicatePasswordValidationException extends WrongDataException {
    private static final String KEY_MESSAGE = "wrong-input-data.duplicate-password-validation-failed";

    public DuplicatePasswordValidationException(ResourceBundle bundle) {
        super(bundle.getString(KEY_MESSAGE));
    }

    public String getModelAttribute() {
        return "duplicatePasswordErrorMessage";
    }
}
