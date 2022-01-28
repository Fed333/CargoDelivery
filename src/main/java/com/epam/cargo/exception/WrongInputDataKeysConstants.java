package com.epam.cargo.exception;

public interface WrongInputDataKeysConstants {
    String NO_FILLED_LOGIN_KEY_ERROR_MESSAGE = "wrong-input-data.no-filled-login";
    String NO_VALID_LOGIN_KEY_ERROR_MESSAGE = "wrong-input-data.no-valid-login";
    String OCCUPIED_LOGIN_KEY_ERROR_FORMAT_MESSAGE = "wrong-input-data.occupied-login.format";

    String NO_FILLED_PASSWORD_KEY_ERROR_MESSAGE = "wrong-input-data.no-filled-password";
    String NO_VALID_PASSWORD_KEY_ERROR_MESSAGE = "wrong-input-data.no-valid-password";
    String SMALL_PASSWORD_KEY_ERROR_MESSAGE = "wrong-input-data.small-password";
    String CONFIRMATION_PASSWORD_FAILED_KEY_ERROR_MESSAGE = "wrong-input-data.duplicate-password-confirmation-failed";

    String NO_FILLED_DUPLICATE_PASSWORD_KEY_ERROR_MESSAGE = "wrong-input-data.no-filled-duplicate-password";

    String NO_EXISTING_CITY_KEY_ERROR_MESSAGE = "wrong-input-data.no-existing-city.format";

    String NO_POSITIVE_NUMBER_KEY_ERROR_MESSAGE = "wrong-input-data.no-positive-number";

    String REQUIRED_KEY_ERROR_MESSAGE = "lang.required";

    String UNKNOWN_ERROR_KEY_MESSAGE = "unknown-error";
}
