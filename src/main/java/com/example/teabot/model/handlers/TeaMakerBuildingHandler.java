package com.example.teabot.model.handlers;

import com.example.teabot.model.ChatInfo;
import com.example.teabot.model.enums.AttributeUpdateStatus;
import com.example.teabot.model.enums.teamaker.MakerSelectingProposals;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

import java.util.Arrays;

public class TeaMakerBuildingHandler implements AttributeHandler {
    @Override
    public String question() {
        return "You want to create tea or ask someone to do it?";
    }

    @Override
    public AttributeUpdateStatus updateAttribute(String data, ChatInfo orderInfo) {
        return Arrays.stream(MakerSelectingProposals.values())
                .filter(proposal -> proposal.getMessage().equals(data))
                .findFirst()
                .map(proposal -> {
                    if (proposal == MakerSelectingProposals.I_WILL_MAKE_TEA) {
                        orderInfo.setTeaMaker(true);
                    }

                    return AttributeUpdateStatus.OK;
                })
                .orElse(AttributeUpdateStatus.BAD);
    }

    @Override
    public ReplyKeyboard getMarkup() {
        return KeyboardFactory.getKeyboardByParameter(MakerSelectingProposals.I_WANT_TEA, question());
    }
}
