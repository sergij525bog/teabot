package com.example.teabot.model.handlers;

import com.example.teabot.model.ChatInfo;
import com.example.teabot.model.Cup;
import com.example.teabot.model.enums.OrderState;
import com.example.teabot.model.enums.cup.Name;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

public class CupNameHandler implements AttributeHandler {

    @Override
    public String question() {
        return "Input cup name";
    }

    @Override
    public OrderState processUserInput(String data, ChatInfo orderInfo) {
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
