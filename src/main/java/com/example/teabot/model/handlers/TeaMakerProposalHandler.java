package com.example.teabot.model.handlers;

import com.example.teabot.model.OrderInfo;
import com.example.teabot.model.enums.OrderState;
import com.example.teabot.model.enums.teamaker.MakerSelectingProposals;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

import java.util.Arrays;

class TeaMakerProposalHandler implements OrderAttributeHandler {
    @Override
    public String question() {
        return "You want to create tea or ask someone to do it?";
    }

    @Override
    public OrderState processUserInput(String data, OrderInfo orderInfo) {
        return Arrays.stream(MakerSelectingProposals.values())
                .filter(proposal -> proposal.getMessage().equals(data))
                .findFirst()
                .map(proposal -> updateTeaMakerAndStates(orderInfo, proposal))
                .orElse(OrderState.ERROR);
    }

    private static OrderState updateTeaMakerAndStates(OrderInfo orderInfo, MakerSelectingProposals proposal) {
        OrderState nextState = updateTeaMakerByProposal(orderInfo, proposal);

        orderInfo.setNextState(nextState);
        orderInfo.setPrevState(nextState, orderInfo.getCurrentState());
        return nextState;
    }

    private static OrderState updateTeaMakerByProposal(OrderInfo orderInfo, MakerSelectingProposals proposal) {
        return switch (proposal) {
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
    }

    @Override
    public ReplyKeyboard getMarkup() {
        return KeyboardFactory.getKeyboardByParameter(MakerSelectingProposals.I_WANT_TEA, question());
    }
}
