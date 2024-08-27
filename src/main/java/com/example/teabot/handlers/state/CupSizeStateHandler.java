package com.example.teabot.handlers.state;

import com.example.teabot.handlers.OrderStateHandler;
import com.example.teabot.model.enums.OrderState;
import com.example.teabot.model.enums.cup.Size;
import com.example.teabot.model.orderInfo.OrderInfo;

class CupSizeStateHandler implements OrderStateHandler<Size> {
    @Override
    public OrderInfo updateOrderState(OrderInfo order, Size param) {
        final OrderState state = OrderState.DELICACY_TYPE_AWAITING;

        order.setPrevState(state, order.getCurrentState());
        order.setCurrentState(state);

        return order;
    }
}
