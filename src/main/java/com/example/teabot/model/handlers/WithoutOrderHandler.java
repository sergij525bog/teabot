package com.example.teabot.model.handlers;

import com.example.teabot.model.ChatInfo;
import com.example.teabot.model.enums.OrderState;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

public class WithoutOrderHandler implements AttributeHandler {
    @Override
    public String question() {
        return "You skipped order for yourself but you will make tea for your comrades! You are true hero!\n" +
                "If you want order tea, please press '/start'";
    }

    @Override
    public OrderState processUserInput(String data, ChatInfo orderInfo) {
        return null;
    }

    @Override
    public ReplyKeyboard getMarkup() {
        return null;
    }
}
