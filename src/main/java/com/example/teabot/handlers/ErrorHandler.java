package com.example.teabot.handlers;

import com.example.teabot.model.enums.OrderState;
import com.example.teabot.model.orderInfo.OrderInfo;
import lombok.AllArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

@AllArgsConstructor
class ErrorHandler implements OrderAttributeHandler {
    private OrderAttributeHandler lastWorkedHandler;

    @Override
    public String question() {
        return "Unexpected input. Please try again";
    }

    @Override
    public OrderState updateOrder(OrderInfo order, String orderAttribute) {
        return lastWorkedHandler.updateOrder(order, orderAttribute);
    }

    @Override
    public ReplyKeyboard getMarkup() {
        return lastWorkedHandler.getMarkup();
    }
}
