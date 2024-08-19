package com.example.teabot.handlers;

import com.example.teabot.model.enums.OrderState;
import com.example.teabot.model.enums.tea.TeaBuildingType;
import com.example.teabot.model.orderInfo.OrderInfo;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

import java.util.Arrays;

class TeaBuildingProposalHandler implements OrderAttributeHandler {
    @Override
    public ReplyKeyboard getMarkup() {
        return KeyboardFactory.getKeyboardByParameter(TeaBuildingType.BY_NAME, question());
    }

    @Override
    public String question() {
        return "What tea do you want?";
    }

    @Override
    public OrderInfo updateOrder(OrderInfo order, String orderAttribute) {
        return Arrays.stream(TeaBuildingType.values())
                .filter(type -> type.getBuildingType().equals(orderAttribute))
                .findFirst()
                .map(type -> updateType(order, type))
                .orElseGet(() -> updateOrderWithError(order));
    }

    private static OrderInfo updateType(OrderInfo order, TeaBuildingType type) {
        final OrderState state = type == TeaBuildingType.BY_DESCRIPTION ?
                OrderState.TYPE_SELECTION_AWAITING :
                OrderState.INPUT_NAME_AWAITING;

        order.setPrevState(state, order.getCurrentState());
        order.setNextState(state);
        order.setCurrentState(state);

        return order;
    }
}
