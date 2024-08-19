package com.example.teabot.handlers;

import com.example.teabot.model.enums.OrderState;
import com.example.teabot.model.enums.tea.Type;
import com.example.teabot.model.orderInfo.OrderInfo;
import com.example.teabot.model.orderInfo.Tea;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

import java.util.Arrays;

class TeaTypeHandler implements OrderAttributeHandler {
    @Override
    public String question() {
        return "Select tea type";
    }

    @Override
    public OrderInfo updateOrder(OrderInfo order, String orderAttribute) {
        return Arrays.stream(Type.values())
                .filter(type -> type.getType().equals(orderAttribute))
                .findFirst()
                .map(type -> updateTeaColor(order, type))
                .orElseGet(() -> updateOrderWithError(order));
    }

    private static OrderInfo updateTeaColor(OrderInfo order, Type type) {
        final Tea tea = order.getTea();
        tea.setType(type);

        order.setCurrentState(OrderState.COLOR_SELECTION_AWAITING);

        return order;
    }

    @Override
    public ReplyKeyboard getMarkup() {
        return KeyboardFactory.getKeyboardByParameter(Type.LEAF_TEA, question());
    }
}
