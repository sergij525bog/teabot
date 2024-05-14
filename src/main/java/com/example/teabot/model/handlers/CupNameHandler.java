package com.example.teabot.model.handlers;

import com.example.teabot.model.ChatInfo;
import com.example.teabot.model.Cup;
import com.example.teabot.model.enums.AttributeUpdateStatus;
import com.example.teabot.model.enums.cup.Name;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

public class CupNameHandler implements AttributeHandler {

    @Override
    public String question() {
        return "Input cup name";
    }

    @Override
    public AttributeUpdateStatus updateAttribute(String data, ChatInfo orderInfo) {
        Cup cup = orderInfo.getCup();
        cup.setName(data);

        return AttributeUpdateStatus.OK;
    }

    @Override
    public ReplyKeyboard getMarkup() {
        return KeyboardFactory.getKeyboardByParameter(Name.BIG_BLUE, question());
    }
}
