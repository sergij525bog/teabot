package com.example.teabot.model.handlers;

import com.example.teabot.model.ChatInfo;
import com.example.teabot.model.enums.AttributeUpdateStatus;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

public class CancelHandler implements AttributeHandler {
    @Override
    public String question() {
        return "Order canceled. Press '/start' if you want create order";
    }

    @Override
    public AttributeUpdateStatus updateAttribute(String data, ChatInfo orderInfo) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ReplyKeyboard getMarkup() {
        return null;
    }
}
