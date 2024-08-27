package com.example.teabot.handlers.attribute;

import com.example.teabot.handlers.OrderAttributeHandler;
import com.example.teabot.model.enums.OrderState;
import com.example.teabot.model.enums.teamaker.MakerSelectingProposals;
import com.example.teabot.model.orderInfo.OrderInfo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TeaMakerProposalHandlerTest {

    private final OrderAttributeHandler handler = new TeaMakerProposalHandler();

    @Test
    void itShouldReturnErrorStateIfDataIsIncorrect() {
        OrderInfo order1 = handler.updateOrder(new OrderInfo(), "");
        OrderInfo order2 = handler.updateOrder(new OrderInfo(), "incorrect input");

        assertEquals(OrderState.ERROR, order1.getCurrentState());
        assertEquals(OrderState.ERROR, order2.getCurrentState());
    }

    @Test
    void itShouldReturnWithoutOrderState() {
        OrderInfo order = new OrderInfo();
        order = handler.updateOrder(order, MakerSelectingProposals.I_CAN_MAKE_TEA.getMessage());

        assertEquals(OrderState.WITHOUT_ORDER, order.getCurrentState());
        assertTrue(order.isTeaMaker());
    }

    @Test
    void itShouldReturnTeaBuildingTypeProposalState() {
        OrderInfo order = new OrderInfo();
        order = handler.updateOrder(order, MakerSelectingProposals.I_WANT_TEA.getMessage());

        assertEquals(OrderState.TEA_BUILDING_TYPE_PROPOSAL, order.getCurrentState());
        assertFalse(order.isTeaMaker());

        order = handler.updateOrder(order, MakerSelectingProposals.I_WANT_TEA_AND_CAN_MAKE_IT.getMessage());

        assertEquals(OrderState.TEA_BUILDING_TYPE_PROPOSAL, order.getCurrentState());
        assertTrue(order.isTeaMaker());
    }
}