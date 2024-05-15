package org.example.businessLayer.validators;

import java.util.regex.Pattern;
import org.example.model.Client;

/**
 * Validator class for validating email addresses of clients.
 */
public class EmailValidator implements Validator<Client> {

    /**
     * Regular expression pattern for validating email addresses.
     */
    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    /**
     * Validates the email address of a client.
     *
     * @param client The client object whose email address needs to be validated.
     * @throws IllegalArgumentException if the email address is not valid.
     */
    @Override
    public void validate(Client client) {
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        if (!pattern.matcher(client.getEmail()).matches()) {
            throw new IllegalArgumentException("Email is not a valid email!");
        }
    }
}
