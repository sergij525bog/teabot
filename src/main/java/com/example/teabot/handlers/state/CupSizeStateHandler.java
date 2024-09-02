package com.example.teabot.handlers.state;

import com.example.teabot.handlers.OrderStateHandler;
import com.example.teabot.model.enums.OrderState;
import com.example.teabot.model.enums.cup.CupSize;
import com.example.teabot.model.orderInfo.OrderInfo;

class CupSizeStateHandler implements OrderStateHandler<CupSize> {
    @Override
    public OrderInfo updateOrderState(OrderInfo order, CupSize param) {
        final OrderState state = OrderState.DELICACY_TYPE_AWAITING;

        order.setPrevState(state, order.getCurrentState());
        order.setCurrentState(state);

        return order;
    }
}
