package com.example.teabot.handlers;

import com.example.teabot.model.orderInfo.OrderInfo;
import com.example.teabot.model.orderInfo.Tea;
import com.example.teabot.model.enums.OrderState;
import com.example.teabot.model.enums.tea.Color;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

import java.util.Arrays;

class ColorHandler implements OrderAttributeHandler {
    @Override
    public ReplyKeyboard getMarkup() {
        return KeyboardFactory.getKeyboardByParameter(Color.BLACK, question());
    }

    @Override
    public String question() {
        return "Select tea color";
    }

    @Override
    public OrderState processUserInput(String data, OrderInfo orderInfo) {
        return Arrays.stream(Color.values())
                .filter(color -> color.getColor().equals(data))
                .findFirst()
                .map(color -> updateColor(orderInfo, color))
                .orElse(OrderState.ERROR);
    }

    private static OrderState updateColor(OrderInfo orderInfo, Color color) {
        Tea tea = orderInfo.getTea();
        tea.setColor(color);

        OrderState state = OrderState.ADDITIONS_AWAITING;
        orderInfo.setPrevState(state, orderInfo.getCurrentState());
        return state;
    }
}
