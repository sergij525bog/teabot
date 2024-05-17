package com.example.teabot.model.handlers;

import com.example.teabot.model.ChatInfo;
import com.example.teabot.model.Tea;
import com.example.teabot.model.enums.OrderState;
import com.example.teabot.model.enums.tea.Color;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

import java.util.Arrays;

public class ColorHandler implements AttributeHandler {
    @Override
    public ReplyKeyboard getMarkup() {
        return KeyboardFactory.getKeyboardByParameter(Color.BLACK, question());
    }

    @Override
    public String question() {
        return "Select tea color";
    }

    @Override
    public OrderState processUserInput(String data, ChatInfo orderInfo) {
        return Arrays.stream(Color.values())
                .filter(color -> color.getColor().equals(data))
                .findFirst()
                .map(color -> {
                    Tea tea = orderInfo.getTea();
                    tea.setColor(color);

                    OrderState state = OrderState.ADDITIONS_AWAITING;
                    orderInfo.setPrevState(state, orderInfo.getCurrentState());
                    return state;
                })
                .orElse(OrderState.ERROR);
    }
}
