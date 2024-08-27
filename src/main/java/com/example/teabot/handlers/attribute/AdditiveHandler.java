package com.example.teabot.handlers.attribute;

import com.example.teabot.handlers.OrderAttributeHandler;
import com.example.teabot.model.enums.tea.Additive;
import com.example.teabot.model.orderInfo.OrderInfo;
import com.example.teabot.model.orderInfo.Tea;

import java.util.Set;

class AdditiveHandler implements OrderAttributeHandler<Additive> {

    @Override
    public OrderInfo updateOrder(OrderInfo order, Additive attribute) {
        final Tea tea = order.getTea();
        final Set<Additive> additives = tea.getAdditives();

        if (attribute != Additive.NONE) {
            additives.add(attribute);
        } else {
            additives.clear();
        }

        return order;
    }
}
