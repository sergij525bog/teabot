package com.example.teabot.model.handlers;

import com.example.teabot.model.Cup;
import com.example.teabot.model.OrderInfo;
import com.example.teabot.model.enums.OrderState;
import com.example.teabot.model.enums.cup.Size;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

import java.util.Arrays;

class CupSizeHandler implements OrderAttributeHandler {
    @Override
    public String question() {
        return "input cup size";
    }

    @Override
    public OrderState processUserInput(String data, OrderInfo orderInfo) {
        return Arrays.stream(Size.values())
                .filter(size -> size.getSize().equals(data))
                .findFirst()
                .map(size -> updateCupSize(data, orderInfo))
                .orElse(OrderState.ERROR);
    }

    private static OrderState updateCupSize(String data, OrderInfo orderInfo) {
        Cup cup = orderInfo.getCup();
        cup.setSize(data);

        OrderState state = OrderState.DELICACY_TYPE_AWAITING;
        orderInfo.setPrevState(state, orderInfo.getCurrentState());
        return state;
    }

    @Override
    public ReplyKeyboard getMarkup() {
        return KeyboardFactory.getKeyboardByParameter(Size.BIG, question());
    }
}
