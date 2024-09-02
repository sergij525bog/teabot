package com.example.teabot.handlers.state;

import com.example.teabot.handlers.OrderStateHandler;
import com.example.teabot.model.enums.OrderState;
import com.example.teabot.model.enums.teamaker.MakerSelectingProposals;
import com.example.teabot.model.orderInfo.OrderInfo;
import com.example.teabot.utils.TestUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TeaMakerProposalStateHandlerTest {

    public static final OrderState INITIAL_STATE = OrderState.TEA_MAKER_BUILDING_PROPOSAL;
    private final OrderStateHandler<MakerSelectingProposals> handler = new TeaMakerProposalStateHandler();

    @Test
    void itShouldSwitchToTeaBuildingTypeSelection() {
        OrderInfo order1 = TestUtils.getOrder(INITIAL_STATE);
        OrderInfo order2 = TestUtils.getOrder(INITIAL_STATE);

        final OrderState expected = OrderState.TEA_BUILDING_TYPE_PROPOSAL;

        order1 = handler.updateOrderState(
                order1,
                MakerSelectingProposals.I_WANT_TEA);
        order2 = handler.updateOrderState(
                order2,
                MakerSelectingProposals.I_WANT_TEA_AND_CAN_MAKE_IT);

        assertEquals(expected, order1.getCurrentState());
        assertEquals(INITIAL_STATE, order1.getPrevState());

        assertEquals(expected, order2.getCurrentState());
        assertEquals(INITIAL_STATE, order2.getPrevState());
    }

    @Test
    void itShouldFinishOrderCreation() {
        OrderInfo order = TestUtils.getOrder(INITIAL_STATE);

        order = handler.updateOrderState(
                order,
                MakerSelectingProposals.I_CAN_MAKE_TEA);

        assertEquals(OrderState.WITHOUT_ORDER, order.getCurrentState());
    }
}