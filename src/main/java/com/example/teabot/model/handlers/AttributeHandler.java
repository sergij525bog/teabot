package com.example.teabot.model.handlers;

import com.example.teabot.model.ChatInfo;
import com.example.teabot.model.enums.OrderState;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

public interface AttributeHandler {
    String question();

    OrderState processUserInput(String data, ChatInfo orderInfo);

    ReplyKeyboard getMarkup();
}
