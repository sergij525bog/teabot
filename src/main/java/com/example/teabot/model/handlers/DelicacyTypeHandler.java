package com.example.teabot.model.handlers;

import com.example.teabot.model.ChatInfo;
import com.example.teabot.model.Delicacy;
import com.example.teabot.model.enums.OrderState;
import com.example.teabot.model.enums.delicacy.DelicacyType;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

public class DelicacyTypeHandler implements AttributeHandler {

    @Override
    public String question() {
        return "Input delicacy type";
    }

    @Override
    public OrderState processUserInput(String data, ChatInfo orderInfo) {
//        todo: add string validation
        Delicacy delicacy = orderInfo.getDelicacy();
        delicacy.setType(data);

        if (DelicacyType.NONE.getType().equals(data)) {
            return OrderState.SAVE_ORDER_AWAITING;
        }

        return OrderState.DELICACY_COUNT_AWAITING;
    }

    @Override
    public ReplyKeyboard getMarkup() {
        return KeyboardFactory.getKeyboardByParameter(DelicacyType.CAKE, question());
    }
}
