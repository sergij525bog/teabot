package com.example.teabot.handlers.attribute;

import com.example.teabot.handlers.OrderAttributeHandler;
import com.example.teabot.model.enums.tea.Additive;
import com.example.teabot.model.orderInfo.OrderInfo;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class AdditiveHandlerTest {
    private final OrderAttributeHandler<Additive> handler = new AdditiveHandler();

    @Test
    void itShouldSaveAdditivesWithoutDuplicates() {
        final Set<Additive> expected = Set.of(Additive.HONEY, Additive.GINGER, Additive.LEMON);
        final OrderInfo order = new OrderInfo();

        handler.updateOrder(order, Additive.HONEY);
        handler.updateOrder(order, Additive.GINGER);
        handler.updateOrder(order, Additive.LEMON);
        handler.updateOrder(order, Additive.HONEY);

        assertEquals(expected, order.getTea().getAdditives());
    }

    @Test
    void itShouldDoesNotSaveAnyAdditivesIfUserSelectNone() {
        final OrderInfo order = new OrderInfo();

        handler.updateOrder(order, Additive.HONEY);
        handler.updateOrder(order, Additive.GINGER);
        handler.updateOrder(order, Additive.LEMON);

        assertFalse(order.getTea().getAdditives().isEmpty());

        handler.updateOrder(order, Additive.NONE);

        assertTrue(order.getTea().getAdditives().isEmpty());
    }
}