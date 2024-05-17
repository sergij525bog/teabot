package com.example.teabot.model.handlers;

import com.example.teabot.model.ChatInfo;
import com.example.teabot.model.enums.OrderState;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

public class StartHandler implements AttributeHandler {

    @Override
    public ReplyKeyboard getMarkup() {
        return null;
    }

    @Override
    public String question() {
        return "You want to create tea or ask someone to do it?";
    }

    @Override
    public OrderState processUserInput(String data, ChatInfo orderInfo) {
        if ("/start".equals(data) || "/start@YouAreTheTea_bot".equals(data)) {
            return OrderState.TEA_MAKER_BUILDING_PROPOSAL;
        }

        return orderInfo.getCurrentState();
    }
}
