package com.example.teabot.handlers.attribute;

import com.example.teabot.handlers.OrderAttributeHandler;
import com.example.teabot.model.enums.tea.Color;
import com.example.teabot.model.orderInfo.OrderInfo;
import com.example.teabot.model.orderInfo.Tea;

class ColorHandler implements OrderAttributeHandler<Color> {

    @Override
    public OrderInfo updateOrder(OrderInfo order, Color attribute) {
        final Tea tea = order.getTea();
        tea.setColor(attribute);

        return order;
    }
}
