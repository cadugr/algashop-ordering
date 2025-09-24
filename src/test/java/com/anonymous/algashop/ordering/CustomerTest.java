package com.anonymous.algashop.ordering;

import com.anonymous.algashop.ordering.domain.entity.Customer;
import org.junit.jupiter.api.Test;

import java.util.UUID;

class CustomerTest {

    @Test
    void testingCustomer() {
        Customer customer = new Customer();
        customer.setId(UUID.randomUUID());
        customer.setFullName("Carlos Eduardo");
        customer.setDocument("1234");
        customer.setLoyaltyPoints(10);
    }
}
