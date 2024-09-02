package com.example.teabot.handlers.state;

import com.example.teabot.handlers.OrderStateHandler;
import com.example.teabot.model.enums.OrderState;
import com.example.teabot.model.enums.tea.Color;
import com.example.teabot.model.orderInfo.OrderInfo;
import com.example.teabot.utils.TestUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ColorStateHandlerTest {

    private static final OrderState INITIAL_STATE = OrderState.COLOR_SELECTION_AWAITING;
    private final OrderStateHandler<Color> handler = new ColorStateHandler();

    @Test
    void itShouldSaveState() {
        OrderInfo order = TestUtils.getOrder(INITIAL_STATE);

        final Color color = TestUtils.getRandomAttribute(Color.values());

        order = handler.updateOrderState(order, color);

        assertEquals(OrderState.ADDITIONS_AWAITING, order.getCurrentState());
        assertEquals(INITIAL_STATE, order.getPrevState());
    }
}