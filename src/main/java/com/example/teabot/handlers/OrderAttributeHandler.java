package com.example.teabot.handlers;

import com.example.teabot.model.enums.OrderState;
import com.example.teabot.model.orderInfo.OrderInfo;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

public interface OrderAttributeHandler {
    String question();

    OrderInfo updateOrder(OrderInfo order, String orderAttribute);

    ReplyKeyboard getMarkup();

    default OrderInfo updateOrderWithError(OrderInfo order) {
        order.setCurrentState(OrderState.ERROR);
        return order;
    }
}
