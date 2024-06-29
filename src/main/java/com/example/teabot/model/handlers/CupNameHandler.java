package com.example.teabot.model.handlers;

import com.example.teabot.model.Cup;
import com.example.teabot.model.OrderInfo;
import com.example.teabot.model.enums.OrderState;
import com.example.teabot.model.enums.cup.Name;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

class CupNameHandler implements OrderAttributeHandler {

    @Override
    public String question() {
        return "Input cup name";
    }

    @Override
    public OrderState processUserInput(String data, OrderInfo orderInfo) {
//        todo: add string validation
        Cup cup = orderInfo.getCup();
        cup.setName(data);

        OrderState state = OrderState.DELICACY_TYPE_AWAITING;
        orderInfo.setPrevState(state, orderInfo.getCurrentState());
        return state;
    }

    @Override
    public ReplyKeyboard getMarkup() {
        return KeyboardFactory.getKeyboardByParameter(Name.BIG_BLUE, question());
    }
}
