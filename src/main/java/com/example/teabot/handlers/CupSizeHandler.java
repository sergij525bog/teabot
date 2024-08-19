package com.example.teabot.handlers;

import com.example.teabot.model.enums.OrderState;
import com.example.teabot.model.enums.cup.Size;
import com.example.teabot.model.orderInfo.Cup;
import com.example.teabot.model.orderInfo.OrderInfo;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

import java.util.Arrays;

class CupSizeHandler implements OrderAttributeHandler {
    @Override
    public String question() {
        return "input cup size";
    }

    @Override
    public OrderInfo updateOrder(OrderInfo order, String orderAttribute) {
        return Arrays.stream(Size.values())
                .filter(size -> size.getSize().equals(orderAttribute))
                .findFirst()
                .map(size -> updateCupSize(orderAttribute, order))
                .orElseGet(() -> updateOrderWithError(order));
    }

    private static OrderInfo updateCupSize(String data, OrderInfo order) {
        final Cup cup = order.getCup();
        cup.setSize(data);

        final OrderState state = OrderState.DELICACY_TYPE_AWAITING;
        order.setPrevState(state, order.getCurrentState());
        order.setCurrentState(state);

        return order;
    }

    @Override
    public ReplyKeyboard getMarkup() {
        return KeyboardFactory.getKeyboardByParameter(Size.BIG, question());
    }
}
