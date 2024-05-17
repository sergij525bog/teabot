package com.example.teabot.model.handlers;

import com.example.teabot.model.ChatInfo;
import com.example.teabot.model.Tea;
import com.example.teabot.model.enums.OrderState;
import com.example.teabot.model.enums.tea.TeaNames;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

public class TeaNameHandler implements AttributeHandler {
    @Override
    public String question() {
        return "Select or input tea name";
    }

    @Override
    public OrderState processUserInput(String data, ChatInfo orderInfo) {
        //        todo: add string validation
        Tea tea = orderInfo.getTea();
        tea.setName(data);

        OrderState state = OrderState.ADDITIONS_AWAITING;
        orderInfo.setPrevState(state, orderInfo.getCurrentState());
        return state;
    }

    @Override
    public ReplyKeyboard getMarkup() {
        return KeyboardFactory.getKeyboardByParameter(TeaNames.LOVARE, question());
    }
}
