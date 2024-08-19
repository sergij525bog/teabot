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
    public OrderInfo updateOrder(OrderInfo order, String orderAttribute) {
        return Arrays.stream(MakerSelectingProposals.values())
                .filter(proposal -> proposal.getMessage().equals(orderAttribute))
                .findFirst()
                .map(proposal -> updateTeaMakerAndStates(order, proposal))
                .orElseGet(() -> updateOrderWithError(order));
    }

    private static OrderInfo updateTeaMakerAndStates(OrderInfo order, MakerSelectingProposals proposal) {
        final OrderState nextState = updateTeaMakerByProposal(order, proposal);

        order.setNextState(nextState);
        order.setPrevState(nextState, order.getCurrentState());
        order.setCurrentState(nextState);

        return order;
    }

    private static OrderState updateTeaMakerByProposal(OrderInfo order, MakerSelectingProposals proposal) {
        final boolean setTeaMaker = proposal != MakerSelectingProposals.I_WANT_TEA;
        order.setTeaMaker(setTeaMaker);

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
