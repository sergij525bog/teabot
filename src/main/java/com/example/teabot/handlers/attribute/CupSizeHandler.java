package com.example.teabot.handlers.attribute;

import com.example.teabot.handlers.OrderAttributeHandler;
import com.example.teabot.model.enums.cup.Size;
import com.example.teabot.model.orderInfo.Cup;
import com.example.teabot.model.orderInfo.OrderInfo;

class CupSizeHandler implements OrderAttributeHandler<Size> {

    @Override
    public OrderInfo updateOrder(OrderInfo order, Size attribute) {
        final Cup cup = order.getCup();
        cup.setSize(attribute.getSize());

        return order;
    }
}
