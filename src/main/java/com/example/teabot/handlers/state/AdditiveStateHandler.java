package com.example.teabot.handlers.state;

import com.example.teabot.handlers.OrderStateHandler;
import com.example.teabot.model.enums.OrderState;
import com.example.teabot.model.enums.tea.Additive;
import com.example.teabot.model.orderInfo.OrderInfo;

class AdditiveStateHandler implements OrderStateHandler<Additive> {
    @Override
    public OrderInfo updateOrderState(OrderInfo order, Additive additive) {
        final OrderState state = additive != Additive.NONE
                ? OrderState.ADDITIONS_AWAITING
                : OrderState.CUP_BUILDING_TYPE_PROPOSAL;
        order.setCurrentState(state);

        return order;
    }
}
