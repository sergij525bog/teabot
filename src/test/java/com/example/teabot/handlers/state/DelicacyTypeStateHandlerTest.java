package com.example.teabot.handlers.state;

import com.example.teabot.handlers.OrderStateHandler;
import com.example.teabot.model.enums.OrderState;
import com.example.teabot.model.enums.delicacy.DelicacyType;
import com.example.teabot.model.orderInfo.OrderInfo;
import com.example.teabot.utils.TestUtils;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DelicacyTypeStateHandlerTest {

    private static final OrderState INITIAL_STATE = OrderState.DELICACY_TYPE_AWAITING;
    private final OrderStateHandler<DelicacyType> handler = new DelicacyTypeStateHandler();

    @Test
    void itShouldSwitchToDelicacyCountSelectionWhenUserDoesNotSelectNoneType() {
        OrderInfo order = TestUtils.getOrder(INITIAL_STATE);

        final DelicacyType type = TestUtils.getRandomAttribute(valuesWithoutNone());

        order = handler.updateOrderState(order, type);

        assertEquals(OrderState.DELICACY_COUNT_AWAITING, order.getCurrentState());
        assertEquals(INITIAL_STATE, order.getPrevState());
    }

    @Test
    void itShouldSwitchToSavingOrder() {
        OrderInfo order = TestUtils.getOrder(INITIAL_STATE);

        order = handler.updateOrderState(order, DelicacyType.NONE);

        assertEquals(OrderState.SAVE_ORDER_AWAITING, order.getCurrentState());
    }

    private DelicacyType[] valuesWithoutNone() {
        return Arrays.stream(DelicacyType.values())
                .filter(type -> type != DelicacyType.NONE)
                .toArray(DelicacyType[]::new);
    }
}