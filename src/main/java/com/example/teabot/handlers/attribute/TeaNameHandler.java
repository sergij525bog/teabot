package com.example.teabot.handlers.attribute;

import com.example.teabot.handlers.OrderAttributeHandler;
import com.example.teabot.model.enums.tea.TeaName;
import com.example.teabot.model.orderInfo.OrderInfo;
import com.example.teabot.model.orderInfo.Tea;

class TeaNameHandler implements OrderAttributeHandler<TeaName> {

    @Override
    public OrderInfo updateOrder(OrderInfo order, TeaName attribute) {
        final Tea tea = order.getTea();
        tea.setName(attribute.getValue());

        return order;
    }
}
