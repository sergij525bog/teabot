package com.example.teabot.handlers.attribute;

import com.example.teabot.handlers.OrderAttributeHandler;
import com.example.teabot.model.enums.delicacy.DelicacyType;
import com.example.teabot.model.orderInfo.Delicacy;
import com.example.teabot.model.orderInfo.OrderInfo;

class DelicacyTypeHandler implements OrderAttributeHandler<DelicacyType> {

    @Override
    public OrderInfo updateOrder(OrderInfo order, DelicacyType attribute) {
        final Delicacy delicacy = order.getDelicacy();
        delicacy.setType(attribute);

        return order;
    }
}
