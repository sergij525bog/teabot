package com.example.teabot.model.handlers;

import com.example.teabot.model.ChatInfo;
import com.example.teabot.model.Tea;
import com.example.teabot.model.enums.AttributeUpdateStatus;
import com.example.teabot.model.enums.tea.TeaNames;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

public class TeaNameHandler implements AttributeHandler {
    @Override
    public String question() {
        return "Select or input tea name";
    }

    @Override
    public AttributeUpdateStatus updateAttribute(String data, ChatInfo orderInfo) {
        Tea tea = orderInfo.getTea();
        tea.setName(data);

        return AttributeUpdateStatus.OK;
    }

    @Override
    public ReplyKeyboard getMarkup() {
        return KeyboardFactory.getKeyboardByParameter(TeaNames.LOVARE, question());
    }
}
