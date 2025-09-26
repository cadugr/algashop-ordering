package com.anonymous.algashop.ordering.domain.valueobject;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Objects;

import static com.anonymous.algashop.ordering.domain.exceptions.ErrorMessages.VALIDATION_ERROR_BIRTHDATE_MUST_IN_PAST;

public record BirthDate(LocalDate value) {

    public BirthDate(LocalDate value) {
        Objects.requireNonNull(value);
        if (value.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException(VALIDATION_ERROR_BIRTHDATE_MUST_IN_PAST);
        }
        this.value = value;
    }

    public Integer age() {
        return (int) Duration.between(value, LocalDate.now()).toDays();
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
