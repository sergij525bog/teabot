package com.example.teabot.handlers.attribute;

import com.example.teabot.handlers.OrderAttributeHandler;
import com.example.teabot.model.enums.cup.CupName;
import com.example.teabot.model.orderInfo.Cup;
import com.example.teabot.model.orderInfo.OrderInfo;

class CupNameHandler implements OrderAttributeHandler<CupName> {

    @Override
    public OrderInfo updateOrder(OrderInfo order, CupName attribute) {
        final Cup cup = order.getCup();

        cup.setCupName(attribute);
        cup.setSize(null);

        return order;
    }
}
