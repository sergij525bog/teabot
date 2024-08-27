package com.example.teabot.handlers.state;

import com.example.teabot.handlers.OrderStateHandler;
import com.example.teabot.model.enums.OrderState;
import com.example.teabot.model.enums.tea.TeaName;
import com.example.teabot.model.orderInfo.OrderInfo;

class TeaNameStateHandler implements OrderStateHandler<TeaName> {
    @Override
    public OrderInfo updateOrderState(OrderInfo order, TeaName param) {
        final OrderState state = OrderState.ADDITIONS_AWAITING;

        order.setPrevState(state, order.getCurrentState());
        order.setCurrentState(state);

        return order;
    }
}
