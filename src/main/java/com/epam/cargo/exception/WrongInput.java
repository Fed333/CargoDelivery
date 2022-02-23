package com.epam.cargo.exception;

public interface WrongInput {

    String NO_FILLED_LOGIN = "wrong-input-data.no-filled-login";
    String NO_VALID_LOGIN = "wrong-input-data.no-valid-login";
    String OCCUPIED_LOGIN_FORMAT = "wrong-input-data.occupied-login.format";
    String NO_FILLED_PASSWORD = "wrong-input-data.no-filled-password";
    String NO_VALID_PASSWORD = "wrong-input-data.no-valid-password";
    String INCORRECT_NAME = "wrong-input.incorrect.name";
    String INCORRECT_SURNAME = "wrong-input.incorrect.surname";
    String SMALL_PASSWORD = "wrong-input-data.small-password";
    String CONFIRMATION_PASSWORD_FAILED = "wrong-input-data.duplicate-password-confirmation-failed";
    String MISSING_DUPLICATE_PASSWORD = "wrong-input-data.no-filled-duplicate-password";
    String NO_EXISTING_CITY = "wrong-input-data.no-existing-city.format";
    String NO_EXISTING_DIRECTION = "wrong-input-data.no-existing-direction.format";
    String NO_POSITIVE_NUMBER = "wrong-input-data.no-positive-number";
    String REQUIRED = "lang.required";
    String INCORRECT_NUMBER_FORMAT = "wrong-input-data.incorrect-number-format";
    String USE_DOT_INSTEAD_OF_COMMA = "wrong-input-data.use-dot-instead-of-comma";
    String INVALID_DIRECTION_SAME_CITIES = "wrong-input-data.invalid-city-direction.same-cities";
    String INVALID_CITY_DIRECTION = "wrong-input-data.invalid-city-direction";
    String UNKNOWN_ERROR = "unknown-error";
    String REQUIRED_BEING_AFTER_SENDING = "wrong-input-data.date.receiving.required-being-after-sending";

    String INCORRECT_PHONE = "wrong-input.incorrect.phone";
}
