package com.example.teabot.handlers.attribute;

import com.example.teabot.handlers.OrderAttributeHandler;
import com.example.teabot.model.enums.cup.CupSize;
import com.example.teabot.model.orderInfo.Cup;
import com.example.teabot.model.orderInfo.OrderInfo;

class CupSizeHandler implements OrderAttributeHandler<CupSize> {

    @Override
    public OrderInfo updateOrder(OrderInfo order, CupSize attribute) {
        final Cup cup = order.getCup();

        cup.setSize(attribute.getSize());
        cup.setCupName(null);

        return order;
    }
}
