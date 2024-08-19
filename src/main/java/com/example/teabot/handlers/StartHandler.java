package com.example.teabot.handlers;

import com.example.teabot.model.enums.OrderState;
import com.example.teabot.model.orderInfo.OrderInfo;
import com.example.teabot.utils.StringUtil;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

class StartHandler implements OrderAttributeHandler {

    @Override
    public ReplyKeyboard getMarkup() {
        return null;
    }

    @Override
    public String question() {
        return "You want to create tea or ask someone to do it?";
    }

    @Override
    public OrderInfo updateOrder(OrderInfo order, String orderAttribute) {
        final OrderState state = StringUtil.isStartCommand(orderAttribute)
                ? OrderState.TEA_MAKER_BUILDING_PROPOSAL
                : OrderState.ERROR;
        order.setCurrentState(state);

        return order;
    }
}
