package com.example.teabot.handlers.attribute;

import com.example.teabot.handlers.OrderAttributeHandler;
import com.example.teabot.model.enums.tea.TeaBuildingType;
import com.example.teabot.model.orderInfo.OrderInfo;

class TeaBuildingProposalHandler implements OrderAttributeHandler<TeaBuildingType> {

    @Override
    public OrderInfo updateOrder(OrderInfo order, TeaBuildingType attribute) {
        return order;
    }
}
