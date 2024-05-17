package com.example.teabot.model.handlers;

import com.example.teabot.model.ChatInfo;
import com.example.teabot.model.enums.OrderState;
import com.example.teabot.model.enums.cup.CupBuildingType;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

import java.util.Arrays;

public class CupBuildingTypeHandler implements AttributeHandler {

    @Override
    public String question() {
        return "What cup do you want?";
    }

    @Override
    public OrderState processUserInput(String data, ChatInfo orderInfo) {
        return Arrays.stream(CupBuildingType.values())
                .filter(type -> type.getType().equals(data))
                .findFirst()
                .map(type -> {
                    OrderState state = type == CupBuildingType.BY_NAME ?
                            OrderState.CUP_NAME_AWAITING :
                            OrderState.CUP_SIZE_AWAITING;
                    orderInfo.setNextState(state);

                    return state;
                })
                .orElse(OrderState.ERROR);
    }

    @Override
    public ReplyKeyboard getMarkup() {
        return KeyboardFactory.getKeyboardByParameter(CupBuildingType.BY_NAME, question());
    }
}
