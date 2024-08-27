package com.example.teabot.handlers.state;

import com.example.teabot.handlers.OrderStateHandler;
import com.example.teabot.model.enums.OrderState;
import com.example.teabot.model.enums.cup.Name;
import com.example.teabot.model.orderInfo.OrderInfo;

class CupNameStateHandler implements OrderStateHandler<Name> {
    @Override
    public OrderInfo updateOrderState(OrderInfo order, Name param) {
        final OrderState state = OrderState.DELICACY_TYPE_AWAITING;

        order.setPrevState(state, order.getCurrentState());
        order.setCurrentState(state);

        return order;
    }
}
