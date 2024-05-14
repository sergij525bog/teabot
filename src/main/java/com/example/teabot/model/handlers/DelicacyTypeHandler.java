package com.example.teabot.model.handlers;

import com.example.teabot.model.ChatInfo;
import com.example.teabot.model.Delicacy;
import com.example.teabot.model.enums.AttributeUpdateStatus;
import com.example.teabot.model.enums.delicacy.DelicacyType;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

public class DelicacyTypeHandler implements AttributeHandler {

    @Override
    public String question() {
        return "Input delicacy type";
    }

    @Override
    public AttributeUpdateStatus updateAttribute(String data, ChatInfo orderInfo) {
        Delicacy delicacy = orderInfo.getDelicacy();
        delicacy.setType(data);

        return AttributeUpdateStatus.OK;
    }

    @Override
    public ReplyKeyboard getMarkup() {
        return KeyboardFactory.getKeyboardByParameter(DelicacyType.CAKE, question());
    }
}
