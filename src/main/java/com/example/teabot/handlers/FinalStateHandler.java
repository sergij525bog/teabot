package com.example.teabot.handlers;

import com.example.teabot.model.orderInfo.OrderInfo;
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
    default OrderInfo updateOrder(OrderInfo order, String orderAttribute) {
        throw new UnsupportedOperationException();
    }
}
