package com.example.teabot.model.handlers;

import com.example.teabot.model.ChatInfo;
import com.example.teabot.model.Tea;
import com.example.teabot.model.enums.OrderState;
import com.example.teabot.model.enums.tea.Additive;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

import java.util.Arrays;

public class AdditiveHandler implements AttributeHandler {
    @Override
    public ReplyKeyboard getMarkup() {
        return KeyboardFactory.getKeyboardByParameter(Additive.NONE, question());
    }

    @Override
    public String question() {
        return "Select tea additives";
    }

    @Override
    public OrderState processUserInput(String data, ChatInfo orderInfo) {
        Tea tea = orderInfo.getTea();
        return Arrays.stream(Additive.values())
                .filter(additive -> additive.getAdditive().equals(data))
                .findFirst()
                .map(additive -> update(tea, additive))
                .orElse(OrderState.ERROR);
    }

    private OrderState update(Tea tea, Additive additive) {
        if (additive == Additive.NONE) {
            tea.getAdditives().clear();
            return OrderState.CUP_BUILDING_TYPE_PROPOSAL;
        } else {
            tea.getAdditives().add(additive);
            return OrderState.ADDITIONS_AWAITING;
        }
    }
}
