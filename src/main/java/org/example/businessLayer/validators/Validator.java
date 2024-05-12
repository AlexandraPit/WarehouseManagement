package org.example.businessLayer.validators;

public interface Validator<T> {
    public void validate(T t);
}
