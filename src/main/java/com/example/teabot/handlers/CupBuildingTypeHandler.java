package com.example.teabot.handlers;

import com.example.teabot.model.enums.OrderState;
import com.example.teabot.model.enums.cup.CupBuildingType;
import com.example.teabot.model.orderInfo.OrderInfo;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

import java.util.Arrays;

class CupBuildingTypeHandler implements OrderAttributeHandler {

    @Override
    public String question() {
        return "What cup do you want?";
    }

    @Override
    public OrderInfo updateOrder(OrderInfo order, String orderAttribute) {
        return Arrays.stream(CupBuildingType.values())
                .filter(type -> type.getType().equals(orderAttribute))
                .findFirst()
                .map(type -> updateCupBuildingType(order, type))
                .orElseGet(() -> updateOrderWithError(order));
    }

    private static OrderInfo updateCupBuildingType(OrderInfo orderInfo, CupBuildingType type) {
        final OrderState state = type == CupBuildingType.BY_NAME ?
                OrderState.CUP_NAME_AWAITING :
                OrderState.CUP_SIZE_AWAITING;

        orderInfo.setNextState(state);
        orderInfo.setCurrentState(state);

        return orderInfo;
    }

    @Override
    public ReplyKeyboard getMarkup() {
        return KeyboardFactory.getKeyboardByParameter(CupBuildingType.BY_NAME, question());
    }
}
