package com.example.teabot.model.handlers;

import com.example.teabot.model.ChatInfo;
import com.example.teabot.model.enums.OrderState;
import lombok.AllArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

@AllArgsConstructor
public class ErrorHandler implements AttributeHandler {
    private AttributeHandler handler;

    @Override
    public String question() {
        return "Unexpected input. Please try again";
    }

    @Override
    public OrderState processUserInput(String data, ChatInfo orderInfo) {
        return handler.processUserInput(data, orderInfo);
    }

    @Override
    public ReplyKeyboard getMarkup() {
        return handler.getMarkup();
    }
}
