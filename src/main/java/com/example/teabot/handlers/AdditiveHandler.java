package com.example.teabot.handlers;

import com.example.teabot.model.enums.OrderState;
import com.example.teabot.model.enums.tea.Additive;
import com.example.teabot.model.orderInfo.OrderInfo;
import com.example.teabot.model.orderInfo.Tea;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

import java.util.Arrays;

class AdditiveHandler implements OrderAttributeHandler {
    @Override
    public ReplyKeyboard getMarkup() {
        return KeyboardFactory.getKeyboardByParameter(Additive.NONE, question());
    }

    @Override
    public String question() {
        return "Select tea additives";
    }

    @Override
    public OrderInfo updateOrder(OrderInfo order, String orderAttribute) {
        return Arrays.stream(Additive.values())
                .filter(additive -> additive.getAdditive().equals(orderAttribute))
                .findFirst()
                .map(additive -> updateAdditive(order, additive))
                .orElseGet(() -> updateOrderWithError(order));
    }

    private OrderInfo updateAdditive(OrderInfo orderInfo, Additive additive) {
        final Tea tea = orderInfo.getTea();

        if (additive != Additive.NONE) {
            tea.getAdditives().add(additive);
            orderInfo.setCurrentState(OrderState.ADDITIONS_AWAITING);
        } else {
            tea.getAdditives().clear();
            orderInfo.setCurrentState(OrderState.CUP_BUILDING_TYPE_PROPOSAL);
        }

        return orderInfo;
    }
}
