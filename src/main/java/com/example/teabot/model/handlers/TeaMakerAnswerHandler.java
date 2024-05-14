package com.example.teabot.model.handlers;

import com.example.teabot.model.ChatInfo;
import com.example.teabot.model.enums.AttributeUpdateStatus;
import com.example.teabot.model.enums.teamaker.Answers;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

import java.util.Arrays;

public class TeaMakerAnswerHandler implements AttributeHandler {
    @Override
    public String question() {
        return "Would you like create tea for your chat members?";
    }

    @Override
    public AttributeUpdateStatus updateAttribute(String data, ChatInfo orderInfo) {
        return Arrays.stream(Answers.values())
                .filter(answer -> answer.getAnswer().equals(data))
                .findFirst()
                .map(answer -> {
                    if (answer == Answers.I_CAN_CREATE_TEA) {
                        orderInfo.setTeaMaker(true);
                    }

                    return AttributeUpdateStatus.OK;
                })
                .orElse(AttributeUpdateStatus.BAD);
    }

    @Override
    public ReplyKeyboard getMarkup() {
        return KeyboardFactory.getKeyboardByParameter(Answers.I_CAN_CREATE_TEA, question());
    }
}
