package com.example.teabot.model.handlers;

import com.example.teabot.model.ChatInfo;
import com.example.teabot.model.enums.AttributeUpdateStatus;
import com.example.teabot.model.enums.tea.TeaBuildingType;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

public class InitialHandler implements AttributeHandler {
    @Override
    public ReplyKeyboard getMarkup() {
        return KeyboardFactory.getKeyboardByParameter(TeaBuildingType.BY_NAME, question());
    }

    @Override
    public String question() {
        return "What tea do you want?";
    }

    @Override
    public AttributeUpdateStatus updateAttribute(String data, ChatInfo orderInfo) {
        throw new UnsupportedOperationException();
    }
}
