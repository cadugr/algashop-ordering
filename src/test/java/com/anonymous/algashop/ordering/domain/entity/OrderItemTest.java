package com.anonymous.algashop.ordering.domain.entity;


import com.anonymous.algashop.ordering.domain.valueobject.Product;
import com.anonymous.algashop.ordering.domain.valueobject.Quantity;
import com.anonymous.algashop.ordering.domain.valueobject.id.OrderId;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class OrderItemTest {

    @Test
    void shouldGenerateBrandNewOrderItem() {
        Product product = ProductTestDataBuilder.aProduct().build();
        Quantity quantity = new Quantity(1);
        OrderId orderId = new OrderId();

        OrderItem orderItem = OrderItem.brandNew()
                .product(product)
                .quantity(quantity)
                .orderId(orderId)
                .build();

        Assertions.assertWith(orderItem,
                oi -> Assertions.assertThat(oi.id()).isNotNull(),
                oi -> Assertions.assertThat(oi.productId()).isEqualTo(product.id()),
                oi -> Assertions.assertThat(oi.productName()).isEqualTo(product.name()),
                oi -> Assertions.assertThat(oi.price()).isEqualTo(product.price()),
                oi -> Assertions.assertThat(oi.quantity()).isEqualTo(quantity),
                oi -> Assertions.assertThat(oi.orderId()).isEqualTo(orderId)
        );
    }

}