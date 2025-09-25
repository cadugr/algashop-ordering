package com.anonymous.algashop.ordering;

import com.anonymous.algashop.ordering.domain.entity.Customer;
import com.anonymous.algashop.ordering.domain.utility.IdGenerator;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

class CustomerTest {

    @Test
    void testingCustomer() {
        Customer customer = new Customer(
                IdGenerator.generateTimeBasedUUID(),
                "John Doe",
                LocalDate.of(1991, 7, 5),
                "johndoe@gmail.com",
                "478-256-2504",
                "255-08-0578",
                true,
                OffsetDateTime.now()
        );

        customer.addLoyaltyPoints(10);

    }
}
