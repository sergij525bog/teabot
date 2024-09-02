package com.example.teabot.handlers.state;

import com.example.teabot.handlers.OrderStateHandler;
import com.example.teabot.model.enums.OrderState;
import com.example.teabot.model.enums.delicacy.DelicacyType;
import com.example.teabot.model.orderInfo.OrderInfo;

class DelicacyTypeStateHandler implements OrderStateHandler<DelicacyType> {
    @Override
    public OrderInfo updateOrderState(OrderInfo order, DelicacyType param) {
        final OrderState state = DelicacyType.NONE == param
                ? OrderState.SAVE_ORDER_AWAITING
                : OrderState.DELICACY_COUNT_AWAITING;

        OrderState currentState = order.getCurrentState();
        order.setCurrentState(state);

        if (!OrderState.isFinal(state)) {
            order.setPrevState(currentState);
        }

        return order;
    }
}
