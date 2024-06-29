package com.example.teabot.model.handlers;

import com.example.teabot.model.OrderInfo;
import com.example.teabot.model.enums.OrderState;
import com.example.teabot.model.enums.cup.CupBuildingType;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

import java.util.Arrays;

class CupBuildingTypeHandler implements OrderAttributeHandler {

    @Override
    public String question() {
        return "What cup do you want?";
    }

    @Override
    public OrderState processUserInput(String data, OrderInfo orderInfo) {
        return Arrays.stream(CupBuildingType.values())
                .filter(type -> type.getType().equals(data))
                .findFirst()
                .map(type -> updateCupBuilingType(orderInfo, type))
                .orElse(OrderState.ERROR);
    }

    private static OrderState updateCupBuilingType(OrderInfo orderInfo, CupBuildingType type) {
        OrderState state = type == CupBuildingType.BY_NAME ?
                OrderState.CUP_NAME_AWAITING :
                OrderState.CUP_SIZE_AWAITING;
        orderInfo.setNextState(state);

        return state;
    }

    @Override
    public ReplyKeyboard getMarkup() {
        return KeyboardFactory.getKeyboardByParameter(CupBuildingType.BY_NAME, question());
    }
}
