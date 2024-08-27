package com.example.teabot.handlers.state;

import com.example.teabot.handlers.OrderStateHandler;
import com.example.teabot.model.enums.OrderState;
import com.example.teabot.model.enums.tea.Type;
import com.example.teabot.model.orderInfo.OrderInfo;

class TeaTypeStateHandler implements OrderStateHandler<Type> {
    @Override
    public OrderInfo updateOrderState(OrderInfo order, Type param) {
        order.setCurrentState(OrderState.COLOR_SELECTION_AWAITING);

        return order;
    }
}
