package com.example.teabot.handlers;

import com.example.teabot.model.enums.OrderState;
import com.example.teabot.model.enums.tea.Color;
import com.example.teabot.model.orderInfo.OrderInfo;
import com.example.teabot.model.orderInfo.Tea;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

import java.util.Arrays;

class ColorHandler implements OrderAttributeHandler {
    @Override
    public ReplyKeyboard getMarkup() {
        return KeyboardFactory.getKeyboardByParameter(Color.BLACK, question());
    }

    @Override
    public String question() {
        return "Select tea color";
    }

    @Override
    public OrderInfo updateOrder(OrderInfo order, String orderAttribute) {
        return Arrays.stream(Color.values())
                .filter(color -> color.getColor().equals(orderAttribute))
                .findFirst()
                .map(color -> updateColor(order, color))
                .orElseGet(() -> updateOrderWithError(order));
    }

    private static OrderInfo updateColor(OrderInfo orderInfo, Color color) {
        final Tea tea = orderInfo.getTea();
        tea.setColor(color);

        final OrderState state = OrderState.ADDITIONS_AWAITING;
        orderInfo.setPrevState(state, orderInfo.getCurrentState());
        orderInfo.setCurrentState(state);

        return orderInfo;
    }
}
