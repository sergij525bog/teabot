package com.example.teabot.handlers;

import com.example.teabot.model.enums.OrderState;
import com.example.teabot.model.enums.teamaker.MakerSelectingProposals;
import com.example.teabot.model.orderInfo.OrderInfo;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

import java.util.Arrays;

class TeaMakerProposalHandler implements OrderAttributeHandler {
    @Override
    public String question() {
        return "You want to create tea or ask someone to do it?";
    }

    @Override
    public OrderState updateOrder(OrderInfo order, String orderAttribute) {
        return Arrays.stream(MakerSelectingProposals.values())
                .filter(proposal -> proposal.getMessage().equals(orderAttribute))
                .findFirst()
                .map(proposal -> updateTeaMakerAndStates(order, proposal))
                .orElse(OrderState.ERROR);
    }

    private static OrderState updateTeaMakerAndStates(OrderInfo orderInfo, MakerSelectingProposals proposal) {
        final OrderState nextState = updateTeaMakerByProposal(orderInfo, proposal);

        orderInfo.setNextState(nextState);
        orderInfo.setPrevState(nextState, orderInfo.getCurrentState());
        return nextState;
    }

    private static OrderState updateTeaMakerByProposal(OrderInfo orderInfo, MakerSelectingProposals proposal) {
        final boolean setTeaMaker = proposal != MakerSelectingProposals.I_WANT_TEA;
        orderInfo.setTeaMaker(setTeaMaker);

        return switch (proposal) {
            case I_CAN_MAKE_TEA -> OrderState.WITHOUT_ORDER;
            case I_WANT_TEA, I_WANT_TEA_AND_CAN_MAKE_IT -> OrderState.TEA_BUILDING_TYPE_PROPOSAL;
        };
    }

    @Override
    public ReplyKeyboard getMarkup() {
        return KeyboardFactory.getKeyboardByParameter(MakerSelectingProposals.I_WANT_TEA, question());
    }
}
