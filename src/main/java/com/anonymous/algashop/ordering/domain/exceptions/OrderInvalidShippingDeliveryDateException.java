package com.anonymous.algashop.ordering.domain.exceptions;

import com.anonymous.algashop.ordering.domain.valueobject.id.OrderId;

import static com.anonymous.algashop.ordering.domain.exceptions.ErrorMessages.ERROR_ORDER_DELIVERY_DATE_CANNOT_BE_IN_THE_PAST;

public class OrderInvalidShippingDeliveryDateException extends DomainException {

    public OrderInvalidShippingDeliveryDateException(OrderId id) {
        super(String.format(ERROR_ORDER_DELIVERY_DATE_CANNOT_BE_IN_THE_PAST, id));
    }
}
