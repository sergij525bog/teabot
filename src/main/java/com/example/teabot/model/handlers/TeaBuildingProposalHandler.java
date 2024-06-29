package com.example.teabot.model.handlers;

import com.example.teabot.model.OrderInfo;
import com.example.teabot.model.enums.OrderState;
import com.example.teabot.model.enums.tea.TeaBuildingType;
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
    public OrderState processUserInput(String data, OrderInfo orderInfo) {
        return Arrays.stream(TeaBuildingType.values())
                .filter(type -> type.getBuildingType().equals(data))
                .findFirst()
                .map(type -> updateType(orderInfo, type))
                .orElse(OrderState.ERROR);
    }

    private static OrderState updateType(OrderInfo orderInfo, TeaBuildingType type) {
        OrderState state = type == TeaBuildingType.BY_DESCRIPTION ?
                OrderState.TYPE_SELECTION_AWAITING :
                OrderState.INPUT_NAME_AWAITING;

        orderInfo.setPrevState(state, orderInfo.getCurrentState());
        orderInfo.setNextState(state);
        return state;
    }
}
