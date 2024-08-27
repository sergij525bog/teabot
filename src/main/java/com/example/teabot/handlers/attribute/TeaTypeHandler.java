package com.example.teabot.handlers.attribute;

import com.example.teabot.handlers.OrderAttributeHandler;
import com.example.teabot.model.enums.tea.Type;
import com.example.teabot.model.orderInfo.OrderInfo;
import com.example.teabot.model.orderInfo.Tea;

class TeaTypeHandler implements OrderAttributeHandler<Type> {

    @Override
    public OrderInfo updateOrder(OrderInfo order, Type attribute) {
        final Tea tea = order.getTea();
        tea.setType(attribute);

        return order;
    }
}
