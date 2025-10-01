package com.anonymous.algashop.ordering.domain.entity;


import com.anonymous.algashop.ordering.domain.valueobject.id.CustomerId;
import org.junit.jupiter.api.Test;

class OrderTest {

    @Test
    public void shouldGenerate() {
        Order order = Order.draft(new CustomerId());
    }

}