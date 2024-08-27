package com.example.teabot.handlers.state;

import com.example.teabot.handlers.OrderStateHandler;
import com.example.teabot.model.enums.OrderState;
import com.example.teabot.model.enums.StartCommand;
import com.example.teabot.model.orderInfo.OrderInfo;

public class StartStateHandler implements OrderStateHandler<StartCommand> {
    @Override
    public OrderInfo updateOrderState(OrderInfo order, StartCommand param) {
        order.setCurrentState(OrderState.TEA_MAKER_BUILDING_PROPOSAL);

        return order;
    }
}
