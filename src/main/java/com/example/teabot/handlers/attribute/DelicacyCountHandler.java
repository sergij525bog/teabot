package com.example.teabot.handlers.attribute;

import com.example.teabot.handlers.OrderAttributeHandler;
import com.example.teabot.model.enums.delicacy.DelicacyCount;
import com.example.teabot.model.orderInfo.Delicacy;
import com.example.teabot.model.orderInfo.OrderInfo;

class DelicacyCountHandler implements OrderAttributeHandler<DelicacyCount> {

    @Override
    public OrderInfo updateOrder(OrderInfo order, DelicacyCount attribute) {
        final Delicacy delicacy = order.getDelicacy();
        delicacy.setCount(attribute);

        return order;
    }
}
