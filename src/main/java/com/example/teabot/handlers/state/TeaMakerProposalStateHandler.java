package com.example.teabot.handlers.state;

import com.example.teabot.handlers.OrderStateHandler;
import com.example.teabot.model.enums.OrderState;
import com.example.teabot.model.enums.teamaker.MakerSelectingProposals;
import com.example.teabot.model.orderInfo.OrderInfo;

class TeaMakerProposalStateHandler implements OrderStateHandler<MakerSelectingProposals> {
    @Override
    public OrderInfo updateOrderState(OrderInfo order, MakerSelectingProposals proposal) {
        final OrderState nextState = switch (proposal) {
            case I_CAN_MAKE_TEA -> OrderState.WITHOUT_ORDER;
            case I_WANT_TEA, I_WANT_TEA_AND_CAN_MAKE_IT -> OrderState.TEA_BUILDING_TYPE_PROPOSAL;
        };

        order.setNextState(nextState);
        if (!OrderState.isFinal(nextState)) {
            order.setPrevState(nextState, order.getCurrentState());
        }
        order.setCurrentState(nextState);

        return order;
    }
}
