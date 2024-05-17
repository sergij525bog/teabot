package com.example.teabot.model.handlers;

import com.example.teabot.model.ChatInfo;
import com.example.teabot.model.enums.OrderState;
import com.example.teabot.model.enums.teamaker.MakerSelectingProposals;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

import java.util.Arrays;

public class TeaMakerProposalHandler implements AttributeHandler {
    @Override
    public String question() {
        return "You want to create tea or ask someone to do it?";
    }

    @Override
    public OrderState processUserInput(String data, ChatInfo orderInfo) {
        return Arrays.stream(MakerSelectingProposals.values())
                .filter(proposal -> proposal.getMessage().equals(data))
                .findFirst()
                .map(proposal -> {
                    OrderState nextState = switch (proposal) {
                        case I_CAN_MAKE_TEA -> {
                            orderInfo.setTeaMaker(true);
                            yield OrderState.WITHOUT_ORDER;
                        }
                        case I_WANT_TEA -> {
                            orderInfo.setTeaMaker(false);
                            yield OrderState.TEA_BUILDING_TYPE_PROPOSAL;
                        }
                        case I_WANT_TEA_AND_CAN_MAKE_IT -> {
                            orderInfo.setTeaMaker(true);
                            yield OrderState.TEA_BUILDING_TYPE_PROPOSAL;
                        }
                    };

                    orderInfo.setNextState(nextState);
                    orderInfo.setPrevState(nextState, orderInfo.getCurrentState());
                    return nextState;
                })
                .orElse(OrderState.ERROR);
    }

    @Override
    public ReplyKeyboard getMarkup() {
        return KeyboardFactory.getKeyboardByParameter(MakerSelectingProposals.I_WANT_TEA, question());
    }
}
