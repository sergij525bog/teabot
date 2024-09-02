package com.example.teabot.handlers.state;

import com.example.teabot.handlers.OrderStateHandler;
import com.example.teabot.model.enums.OrderState;
import com.example.teabot.model.enums.tea.TeaBuildingType;
import com.example.teabot.model.orderInfo.OrderInfo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TeaBuildingProposalStateHandlerTest {

    public static final OrderState INITIAL_STATE = OrderState.TEA_BUILDING_TYPE_PROPOSAL;
    private final OrderStateHandler<TeaBuildingType> handler = new TeaBuildingProposalStateHandler();

    @Test
    void itShouldSwitchToTeaNameSelectionBranch() {
        OrderInfo order = getOrderInfo(INITIAL_STATE);

        order = handler.updateOrderState(order, TeaBuildingType.BY_NAME);

        assertEquals(OrderState.INPUT_NAME_AWAITING, order.getCurrentState());
        assertEquals(INITIAL_STATE, order.getPrevState());
    }

    @Test
    void itShouldSwitchToTeaDescriptionBranch() {
        OrderInfo order = getOrderInfo(INITIAL_STATE);

        order = handler.updateOrderState(order, TeaBuildingType.BY_DESCRIPTION);

        assertEquals(OrderState.TYPE_SELECTION_AWAITING, order.getCurrentState());
        assertEquals(INITIAL_STATE, order.getPrevState());
    }

    private static OrderInfo getOrderInfo(OrderState state) {
        OrderInfo order = new OrderInfo();
        order.setCurrentState(state);
        return order;
    }
}