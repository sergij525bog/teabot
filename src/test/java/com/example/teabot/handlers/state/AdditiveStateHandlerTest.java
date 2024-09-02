package com.example.teabot.handlers.state;

import com.example.teabot.handlers.OrderStateHandler;
import com.example.teabot.model.enums.OrderState;
import com.example.teabot.model.enums.tea.Additive;
import com.example.teabot.model.orderInfo.OrderInfo;
import com.example.teabot.utils.TestUtils;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AdditiveStateHandlerTest {

    private final OrderStateHandler<Additive> handler = new AdditiveStateHandler();
    private static final OrderState INITIAL_STATE = OrderState.ADDITIONS_AWAITING;

    @Test
    void itShouldNotChangeStateWhenUserAddSomeAdditive() {
        OrderInfo order = TestUtils.getOrder(INITIAL_STATE);

        final Additive additive = TestUtils.getRandomAttribute(additives());

        order = handler.updateOrderState(order, additive);

        assertEquals(OrderState.ADDITIONS_AWAITING, order.getCurrentState());
    }

    @Test
    void itShouldSaveNewState() {
        OrderInfo order = TestUtils.getOrder(INITIAL_STATE);

        order = handler.updateOrderState(order, Additive.NONE);

        assertEquals(OrderState.CUP_BUILDING_TYPE_PROPOSAL, order.getCurrentState());
    }

    @Test
    void itShouldRewriteState() {
        OrderInfo order = TestUtils.getOrder(INITIAL_STATE);

        for (int i = 0; i < 3; i++) {
            final Additive additive = TestUtils.getRandomAttribute(additives());

            order = handler.updateOrderState(order, additive);
        }

        assertEquals(OrderState.ADDITIONS_AWAITING, order.getCurrentState());

        order = handler.updateOrderState(new OrderInfo(), Additive.NONE);

        assertEquals(OrderState.CUP_BUILDING_TYPE_PROPOSAL, order.getCurrentState());
    }

    private Additive[] additives() {
        return Arrays.stream(Additive.values())
                .filter(a -> a != Additive.NONE)
                .toArray(Additive[]::new);
    }
}