package com.example.teabot.handlers;

import com.example.teabot.model.orderInfo.OrderInfo;
import com.example.teabot.model.orderInfo.Tea;
import com.example.teabot.model.enums.OrderState;
import com.example.teabot.model.enums.tea.TeaNames;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

class TeaNameHandler implements OrderAttributeHandler {
    @Override
    public String question() {
        return "Select or input tea name";
    }

    @Override
    public OrderState processUserInput(String data, OrderInfo orderInfo) {
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
