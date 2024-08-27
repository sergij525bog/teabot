package com.example.teabot.handlers.attribute;

import com.example.teabot.handlers.OrderAttributeHandler;
import com.example.teabot.model.enums.cup.CupBuildingType;
import com.example.teabot.model.orderInfo.OrderInfo;

class CupBuildingTypeHandler implements OrderAttributeHandler<CupBuildingType> {

    @Override
    public OrderInfo updateOrder(OrderInfo order, CupBuildingType attribute) {
        return order;
    }
}
