package com.example.teabot.handlers.attribute;

import com.example.teabot.handlers.OrderAttributeHandler;
import com.example.teabot.model.enums.StartCommand;
import com.example.teabot.model.orderInfo.OrderInfo;

public class StartHandler implements OrderAttributeHandler<StartCommand> {

    @Override
    public OrderInfo updateOrder(OrderInfo order, StartCommand attribute) {
        return order;
    }
}
