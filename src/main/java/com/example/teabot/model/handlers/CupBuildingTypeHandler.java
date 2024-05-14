package com.example.teabot.model.handlers;

import com.example.teabot.model.ChatInfo;
import com.example.teabot.model.enums.AttributeUpdateStatus;
import com.example.teabot.model.enums.cup.CupBuildingType;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

public class CupBuildingTypeHandler implements AttributeHandler {

    @Override
    public String question() {
        return "What cup do you want?";
    }

    @Override
    public AttributeUpdateStatus updateAttribute(String data, ChatInfo orderInfo) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ReplyKeyboard getMarkup() {
        return KeyboardFactory.getKeyboardByParameter(CupBuildingType.BY_NAME, question());
    }
}
