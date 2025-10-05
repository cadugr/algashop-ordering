package com.anonymous.algashop.ordering.domain.exceptions;

import com.anonymous.algashop.ordering.domain.valueobject.id.OrderId;
import com.anonymous.algashop.ordering.domain.valueobject.id.OrderItemId;

import static com.anonymous.algashop.ordering.domain.exceptions.ErrorMessages.ERROR_ORDER_DOES_NOT_CONTAIN_ITEM;

public class OrderDoesNotContainOrderItemException extends DomainException {
    public OrderDoesNotContainOrderItemException(OrderId id, OrderItemId orderItemId) {
        super(String.format(ERROR_ORDER_DOES_NOT_CONTAIN_ITEM, id, orderItemId));
    }
}
