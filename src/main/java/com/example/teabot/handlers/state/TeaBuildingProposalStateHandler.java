package com.example.teabot.handlers.state;

import com.example.teabot.handlers.OrderStateHandler;
import com.example.teabot.model.enums.OrderState;
import com.example.teabot.model.enums.tea.TeaBuildingType;
import com.example.teabot.model.orderInfo.OrderInfo;

class TeaBuildingProposalStateHandler implements OrderStateHandler<TeaBuildingType> {
    @Override
    public OrderInfo updateOrderState(OrderInfo order, TeaBuildingType type) {
        final OrderState state = type == TeaBuildingType.BY_DESCRIPTION ?
                OrderState.TYPE_SELECTION_AWAITING :
                OrderState.INPUT_NAME_AWAITING;

        order.setPrevState(state, order.getCurrentState());
        order.setNextState(state);
        order.setCurrentState(state);

        return order;
    }
}
