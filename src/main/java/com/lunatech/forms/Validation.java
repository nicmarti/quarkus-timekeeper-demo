package com.lunatech.forms;

import io.vavr.control.Either;


public class Validation<T> {
    public Validation() {
    }

    public Either<FormFieldWithErrors, T> validate(Validatable<T> validatable) {
        return validatable.valid();
    }
}
