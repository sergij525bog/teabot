package com.example.teabot.handlers.attribute;

import com.example.teabot.handlers.OrderAttributeHandler;
import com.example.teabot.model.enums.cup.Name;
import com.example.teabot.model.orderInfo.Cup;
import com.example.teabot.model.orderInfo.OrderInfo;

class CupNameHandler implements OrderAttributeHandler<Name> {

    @Override
    public OrderInfo updateOrder(OrderInfo order, Name attribute) {
        final Cup cup = order.getCup();
        cup.setName(attribute);

        return order;
    }
}
