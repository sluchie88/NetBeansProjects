package org.g10.lottery.service;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public final class Validations {

    public static boolean inRange(int value, int min, int max) {
        return value >= min && value <= max;
    }

    public static boolean isNullOrWhitespace(String value) {
        return value == null || value.trim().length() == 0;
    }

    public static boolean isEmail(String emailAddress) {

        if (isNullOrWhitespace(emailAddress)) {
            return false;
        }

        try {
            InternetAddress addr = new InternetAddress(emailAddress);
            addr.validate();
            return true;
        } catch (AddressException ex) {
            return false;
        }
    }
}
