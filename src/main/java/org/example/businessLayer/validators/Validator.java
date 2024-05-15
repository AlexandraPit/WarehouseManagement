package org.example.businessLayer.validators;

/**
 * Interface for implementing validators.
 *
 * @param <T> The type of object to be validated.
 */
public interface Validator<T> {
    /**
     * Validates an object of type T.
     *
     * @param t The object to be validated.
     */
    void validate(T t);
}
