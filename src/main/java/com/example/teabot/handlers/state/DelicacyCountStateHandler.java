package com.example.teabot.handlers.state;

import com.example.teabot.handlers.OrderStateHandler;
import com.example.teabot.model.enums.OrderState;
import com.example.teabot.model.enums.delicacy.DelicacyCount;
import com.example.teabot.model.orderInfo.OrderInfo;

class DelicacyCountStateHandler implements OrderStateHandler<DelicacyCount> {
    @Override
    public OrderInfo updateOrderState(OrderInfo order, DelicacyCount param) {
        order.setCurrentState(OrderState.SAVE_ORDER_AWAITING);

        return order;
    }
}
