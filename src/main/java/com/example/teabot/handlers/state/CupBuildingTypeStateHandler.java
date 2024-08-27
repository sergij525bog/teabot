package com.example.teabot.handlers.state;

import com.example.teabot.handlers.OrderStateHandler;
import com.example.teabot.model.enums.OrderState;
import com.example.teabot.model.enums.cup.CupBuildingType;
import com.example.teabot.model.orderInfo.OrderInfo;

class CupBuildingTypeStateHandler implements OrderStateHandler<CupBuildingType> {
    @Override
    public OrderInfo updateOrderState(OrderInfo order, CupBuildingType type) {
        final OrderState state = type == CupBuildingType.BY_NAME ?
                OrderState.CUP_NAME_AWAITING :
                OrderState.CUP_SIZE_AWAITING;

        order.setNextState(state);
        order.setCurrentState(state);

        return order;
    }
}
