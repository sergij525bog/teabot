package com.example.teabot.model.handlers;

import com.example.teabot.model.ChatInfo;
import com.example.teabot.model.Delicacy;
import com.example.teabot.model.enums.AttributeUpdateStatus;
import com.example.teabot.model.enums.OrderParameter;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

import java.util.stream.IntStream;

public class DelicacyCountHandler implements AttributeHandler {

    @Override
    public String question() {
        return "Input delicacy count";
    }

    @Override
    public AttributeUpdateStatus updateAttribute(String data, ChatInfo orderInfo) {
        Delicacy delicacy = orderInfo.getDelicacy();
        delicacy.setCount(Byte.parseByte(data));

        return AttributeUpdateStatus.OK;
    }

    @Override
    public ReplyKeyboard getMarkup() {
        OrderParameter orderParameter = () -> IntStream.iterate(1, i -> i + 1)
                .limit(5)
                .mapToObj(String::valueOf);
        return KeyboardFactory.getKeyboardByParameter(orderParameter, question());
    }
}
