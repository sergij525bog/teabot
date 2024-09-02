package com.example.teabot.handlers.state;

import com.example.teabot.handlers.OrderStateHandler;
import com.example.teabot.model.enums.OrderState;
import com.example.teabot.model.enums.delicacy.DelicacyCount;
import com.example.teabot.model.orderInfo.OrderInfo;
import com.example.teabot.utils.TestUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DelicacyCountStateHandlerTest {

    private static final OrderState INITIAL_STATE = OrderState.DELICACY_COUNT_AWAITING;
    private final OrderStateHandler<DelicacyCount> handler = new DelicacyCountStateHandler();

    @Test
    void itShouldSaveNewState() {
        OrderInfo order = TestUtils.getOrder(INITIAL_STATE);

        final DelicacyCount count = TestUtils.getRandomAttribute(DelicacyCount.values());

        order = handler.updateOrderState(order, count);

        assertEquals(OrderState.SAVE_ORDER_AWAITING, order.getCurrentState());
    }
}