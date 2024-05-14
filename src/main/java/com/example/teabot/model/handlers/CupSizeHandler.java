package com.example.teabot.model.handlers;

import com.example.teabot.model.ChatInfo;
import com.example.teabot.model.Cup;
import com.example.teabot.model.enums.AttributeUpdateStatus;
import com.example.teabot.model.enums.cup.Size;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

import java.util.Arrays;

public class CupSizeHandler implements AttributeHandler {
    @Override
    public String question() {
        return "input cup size";
    }

    @Override
    public AttributeUpdateStatus updateAttribute(String data, ChatInfo orderInfo) {
        return Arrays.stream(Size.values())
                .filter(size -> size.getSize().equals(data))
                .findFirst()
                .map(size -> {
                    Cup cup = orderInfo.getCup();
                    cup.setSize(data);

                    return AttributeUpdateStatus.OK;
                })
                .orElse(AttributeUpdateStatus.BAD);
    }

    @Override
    public ReplyKeyboard getMarkup() {
        return KeyboardFactory.getKeyboardByParameter(Size.BIG, question());
    }
}
