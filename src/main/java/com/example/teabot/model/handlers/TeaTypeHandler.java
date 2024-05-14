package com.example.teabot.model.handlers;

import com.example.teabot.model.ChatInfo;
import com.example.teabot.model.Tea;
import com.example.teabot.model.enums.AttributeUpdateStatus;
import com.example.teabot.model.enums.tea.Type;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

import java.util.Arrays;

public class TeaTypeHandler implements AttributeHandler {
    @Override
    public ReplyKeyboard getMarkup() {
        return KeyboardFactory.getKeyboardByParameter(Type.LEAF_TEA, question());
    }

    @Override
    public String question() {
        return "Select tea type";
    }

    @Override
    public AttributeUpdateStatus updateAttribute(String data, ChatInfo orderInfo) {
        return Arrays.stream(Type.values())
                .filter(type -> type.getType().equals(data))
                .findFirst()
                .map(type -> {
                    Tea tea = orderInfo.getTea();
                    tea.setType(type);

                    return AttributeUpdateStatus.OK;
                })
                .orElse(AttributeUpdateStatus.BAD);
    }
}
