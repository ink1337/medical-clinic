package com.medicalclinic.exception;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

import static java.util.ResourceBundle.getBundle;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DictionaryHandler {
    private static final String PATH_TO_ERRORS = "errors";

    public static String getMessage(String key, Object... params) {
        ResourceBundle resourceBundle = getBundle(PATH_TO_ERRORS, Locale.getDefault());
        var message = resourceBundle.getString(key);
        return MessageFormat.format(message, params);
    }
}
