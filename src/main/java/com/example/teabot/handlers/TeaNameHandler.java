package com.example.teabot.handlers;

import com.example.teabot.model.enums.OrderState;
import com.example.teabot.model.enums.tea.TeaNames;
import com.example.teabot.model.orderInfo.OrderInfo;
import com.example.teabot.model.orderInfo.Tea;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

class TeaNameHandler implements OrderAttributeHandler {
    @Override
    public String question() {
        return "Select or input tea name";
    }

    @Override
    public OrderInfo updateOrder(OrderInfo order, String orderAttribute) {
        //        todo: add string validation
        final Tea tea = order.getTea();
        tea.setName(orderAttribute);

        final OrderState state = OrderState.ADDITIONS_AWAITING;
        order.setPrevState(state, order.getCurrentState());
        order.setCurrentState(state);

        return order;
    }

    @Override
    public ReplyKeyboard getMarkup() {
        return KeyboardFactory.getKeyboardByParameter(TeaNames.LOVARE, question());
    }
}
