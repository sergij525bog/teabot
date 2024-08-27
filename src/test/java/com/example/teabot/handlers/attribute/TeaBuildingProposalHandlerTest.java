package com.example.teabot.handlers.attribute;

import com.example.teabot.handlers.OrderAttributeHandler;
import com.example.teabot.model.enums.OrderState;
import com.example.teabot.model.enums.tea.TeaBuildingType;
import com.example.teabot.model.orderInfo.OrderInfo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TeaBuildingProposalHandlerTest {

    final OrderAttributeHandler handler = new TeaBuildingProposalHandler();

    @Test
    void itShouldReturnErrorStateIfDataIsIncorrect() {
        final OrderInfo order1 = handler.updateOrder(new OrderInfo(), "");
        final OrderInfo order2 = handler.updateOrder(new OrderInfo(), "incorrect input");

        assertEquals(OrderState.ERROR, order1.getCurrentState());
        assertEquals(OrderState.ERROR, order2.getCurrentState());
    }

    @Test
    void itShouldReturnTypeSelectionAwaitingState() {
        OrderInfo order = handler.updateOrder(new OrderInfo(), TeaBuildingType.BY_DESCRIPTION.getValue());

        assertEquals(OrderState.TYPE_SELECTION_AWAITING, order.getCurrentState());
    }

    @Test
    void itShouldReturnInputNameAwaitingState() {
        OrderInfo order = handler.updateOrder(new OrderInfo(), TeaBuildingType.BY_NAME.getValue());

        assertEquals(OrderState.INPUT_NAME_AWAITING, order.getCurrentState());
    }
}