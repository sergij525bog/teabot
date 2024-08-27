package com.example.teabot.handlers.attribute;

import com.example.teabot.handlers.OrderAttributeHandler;
import com.example.teabot.model.enums.OrderState;
import com.example.teabot.model.orderInfo.OrderInfo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StartHandlerTest {
    private final OrderAttributeHandler handler = new StartHandler();

    @Test
    void isShouldReturnErrorStateIsDataIsIncorrect() {
        final OrderInfo order = handler.updateOrder(new OrderInfo(), "/statr");
        assertEquals(OrderState.ERROR, order.getCurrentState());
    }

    @Test
    void isShouldProcessSuccessfullyIfDataIsCorrect() {
        final OrderInfo order1 = handler.updateOrder(new OrderInfo(), "/start");
        assertEquals(OrderState.TEA_MAKER_BUILDING_PROPOSAL, order1.getCurrentState());

        final OrderInfo order2 = handler.updateOrder(new OrderInfo(), "/start@YouAreTheTea_bot");
        assertEquals(OrderState.TEA_MAKER_BUILDING_PROPOSAL, order2.getCurrentState());
    }
}
