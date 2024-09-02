package com.example.teabot.handlers.attribute;

import com.example.teabot.handlers.OrderAttributeHandler;
import com.example.teabot.model.enums.teamaker.MakerSelectingProposals;
import com.example.teabot.model.orderInfo.OrderInfo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TeaMakerProposalHandlerTest {

    private final OrderAttributeHandler<MakerSelectingProposals> handler = new TeaMakerProposalHandler();

    @Test
    void itShouldReturnWithoutOrderState() {
        OrderInfo order = new OrderInfo();
        order = handler.updateOrder(order, MakerSelectingProposals.I_CAN_MAKE_TEA);

        assertTrue(order.isTeaMaker());
    }

    @Test
    void itShouldReturnTeaBuildingTypeProposalState() {
        OrderInfo order = new OrderInfo();
        order = handler.updateOrder(order, MakerSelectingProposals.I_WANT_TEA);

        assertFalse(order.isTeaMaker());

        order = handler.updateOrder(order, MakerSelectingProposals.I_WANT_TEA_AND_CAN_MAKE_IT);

        assertTrue(order.isTeaMaker());
    }
}