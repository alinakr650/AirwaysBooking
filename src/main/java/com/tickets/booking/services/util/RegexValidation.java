package com.tickets.booking.services.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexValidation {
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    public static final Pattern VALID_PASSWORD_REGEX =
            Pattern.compile("^(?=.*[0-9])"
                    + "(?=.*[a-z])(?=.*[A-Z])"
                    + "(?=\\S+$).{8,20}$");

    public static boolean validateEmail(String emailStr) {

        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }

    public static boolean validatePassword(String password) {
        if (password == null) {
            return false;
        }

        Matcher matcher = VALID_PASSWORD_REGEX.matcher(password);

        return matcher.matches();
    }

}
