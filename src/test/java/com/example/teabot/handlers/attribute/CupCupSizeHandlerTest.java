package com.example.teabot.handlers.attribute;

import com.example.teabot.handlers.OrderAttributeHandler;
import com.example.teabot.model.enums.cup.CupName;
import com.example.teabot.model.enums.cup.CupSize;
import com.example.teabot.model.orderInfo.OrderInfo;
import com.example.teabot.utils.TestUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class CupCupSizeHandlerTest {
    private final OrderAttributeHandler<CupName> nameHandler = new CupNameHandler();
    private final OrderAttributeHandler<CupSize> sizeHandler = new CupSizeHandler();

    @Test
    void itShouldSaveCupSize() {
        final CupSize cupSize = TestUtils.getRandomAttribute(CupSize.values());

        OrderInfo order = sizeHandler.updateOrder(new OrderInfo(), cupSize);

        assertEquals(cupSize.getSize(), order.getCup().getSize());
    }

    @Test
    void itShouldRemoveCupNameWhenCupSizeSaved() {
        final CupName expected = TestUtils.getRandomAttribute(CupName.values());
        final CupSize cupSize = TestUtils.getRandomAttribute(CupSize.values());

        OrderInfo order = nameHandler.updateOrder(new OrderInfo(), expected);

        assertEquals(expected, order.getCup().getCupName());

        order = sizeHandler.updateOrder(order, cupSize);

        assertEquals(cupSize.getSize(), order.getCup().getSize());
        assertNull(order.getCup().getCupName());
    }
}