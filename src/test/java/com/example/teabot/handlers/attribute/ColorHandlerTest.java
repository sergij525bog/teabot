package com.example.teabot.handlers.attribute;

import com.example.teabot.handlers.OrderAttributeHandler;
import com.example.teabot.model.enums.tea.Color;
import com.example.teabot.model.orderInfo.OrderInfo;
import com.example.teabot.utils.TestUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ColorHandlerTest {
    private final OrderAttributeHandler<Color> handler = new ColorHandler();

    @Test
    void itShouldSaveTeaColor() {
        final Color expected = TestUtils.getRandomAttribute(Color.values());

        final OrderInfo order = handler.updateOrder(new OrderInfo(), expected);

        assertEquals(expected, order.getTea().getColor());
    }
}