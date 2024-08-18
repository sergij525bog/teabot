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
    public OrderState updateOrder(OrderInfo order, String orderAttribute) {
        return Arrays.stream(Additive.values())
                .filter(additive -> additive.getAdditive().equals(orderAttribute))
                .findFirst()
                .map(additive -> updateAdditive(order, additive))
                .orElse(OrderState.ERROR);
    }

    private OrderState updateAdditive(OrderInfo orderInfo, Additive additive) {
        final Tea tea = orderInfo.getTea();

        if (additive != Additive.NONE) {
            tea.getAdditives().add(additive);
            return OrderState.ADDITIONS_AWAITING;
        }

        tea.getAdditives().clear();
        return OrderState.CUP_BUILDING_TYPE_PROPOSAL;
    }
}
