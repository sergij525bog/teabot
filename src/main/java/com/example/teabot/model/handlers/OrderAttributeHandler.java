package com.example.teabot.model.handlers;

import com.example.teabot.model.OrderInfo;
import com.example.teabot.model.enums.OrderState;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

public interface OrderAttributeHandler {
    String question();

    OrderState processUserInput(String data, OrderInfo orderInfo);

    ReplyKeyboard getMarkup();
}
