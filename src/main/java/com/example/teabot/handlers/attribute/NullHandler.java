package com.example.teabot.handlers.attribute;

import com.example.teabot.handlers.OrderAttributeHandler;
import com.example.teabot.model.enums.OrderAttribute;
import com.example.teabot.model.orderInfo.OrderInfo;

class NullHandler implements OrderAttributeHandler<OrderAttribute> {

    @Override
    public OrderInfo updateOrder(OrderInfo order, OrderAttribute attribute) {
        return order;
    }
}
