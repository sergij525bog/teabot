package com.example.teabot.handlers;

import com.example.teabot.model.enums.OrderState;
import com.example.teabot.model.enums.cup.Name;
import com.example.teabot.model.orderInfo.Cup;
import com.example.teabot.model.orderInfo.OrderInfo;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

class CupNameHandler implements OrderAttributeHandler {

    @Override
    public String question() {
        return "Input cup name";
    }

    @Override
    public OrderState updateOrder(OrderInfo order, String orderAttribute) {
//        todo: add string validation
        final Cup cup = order.getCup();
        cup.setName(orderAttribute);

        final OrderState state = OrderState.DELICACY_TYPE_AWAITING;
        order.setPrevState(state, order.getCurrentState());
        return state;
    }

    @Override
    public ReplyKeyboard getMarkup() {
        return KeyboardFactory.getKeyboardByParameter(Name.BIG_BLUE, question());
    }
}
