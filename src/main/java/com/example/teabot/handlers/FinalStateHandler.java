package com.example.teabot.handlers;

import com.example.teabot.model.orderInfo.OrderInfo;
import com.example.teabot.model.enums.OrderState;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;

interface FinalStateHandler extends OrderAttributeHandler {
    ReplyKeyboardRemove REPLY_KEYBOARD_REMOVE = ReplyKeyboardRemove.builder()
            .removeKeyboard(true)
            .selective(true)
            .build();

    @Override
    default ReplyKeyboard getMarkup() {
        return REPLY_KEYBOARD_REMOVE;
    }

    @Override
    default OrderState processUserInput(String data, OrderInfo orderInfo) {
        throw new UnsupportedOperationException();
    }
}
