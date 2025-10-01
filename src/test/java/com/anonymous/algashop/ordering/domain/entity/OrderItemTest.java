package com.anonymous.algashop.ordering.domain.entity;


import com.anonymous.algashop.ordering.domain.valueobject.Money;
import com.anonymous.algashop.ordering.domain.valueobject.ProductName;
import com.anonymous.algashop.ordering.domain.valueobject.Quantity;
import com.anonymous.algashop.ordering.domain.valueobject.id.OrderId;
import com.anonymous.algashop.ordering.domain.valueobject.id.ProductId;
import org.junit.jupiter.api.Test;

class OrderItemTest {

    @Test
    public void shouldGenerate() {
        OrderItem.brandNew()
                .productId(new ProductId())
                .quantity(new Quantity(1))
                .orderId(new OrderId())
                .productName(new ProductName("Mouse pad"))
                .price(new Money("100")).build();
    }

}