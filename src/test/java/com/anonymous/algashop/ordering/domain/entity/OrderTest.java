package com.anonymous.algashop.ordering.domain.entity;


import com.anonymous.algashop.ordering.domain.valueobject.Money;
import com.anonymous.algashop.ordering.domain.valueobject.ProductName;
import com.anonymous.algashop.ordering.domain.valueobject.Quantity;
import com.anonymous.algashop.ordering.domain.valueobject.id.CustomerId;
import com.anonymous.algashop.ordering.domain.valueobject.id.ProductId;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class OrderTest {

    @Test
    void shouldGenerate() {
        Order order = Order.draft(new CustomerId());
    }

    @Test
    void shouldAddItem() {
        Order order = Order.draft(new CustomerId());
        ProductId productId = new ProductId();

        order.addItem(
                productId,
                new ProductName("Mouse pad"),
                new Money("100"),
                new Quantity(1)
        );

        Assertions.assertThat(order.items()).hasSize(1);

        OrderItem orderItem = order.items().iterator().next();

        Assertions.assertWith(orderItem,
                (i) -> Assertions.assertThat(i.id()).isNotNull(),
                (i) -> Assertions.assertThat(i.productName()).isEqualTo(new ProductName("Mouse pad")),
                (i) -> Assertions.assertThat(i.productId()).isEqualTo(productId),
                (i) -> Assertions.assertThat(i.price()).isEqualTo(new Money("100")),
                (i) -> Assertions.assertThat(i.quantity()).isEqualTo(new Quantity(1))
                );

    }

}