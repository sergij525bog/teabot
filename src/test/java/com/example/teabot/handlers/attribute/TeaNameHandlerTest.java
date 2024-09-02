package com.example.teabot.handlers.attribute;

import com.example.teabot.handlers.OrderAttributeHandler;
import com.example.teabot.model.enums.tea.Color;
import com.example.teabot.model.enums.tea.TeaName;
import com.example.teabot.model.enums.tea.Type;
import com.example.teabot.model.orderInfo.OrderInfo;
import com.example.teabot.model.orderInfo.Tea;
import com.example.teabot.utils.TestUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class TeaNameHandlerTest {

    private final OrderAttributeHandler<TeaName> nameHandler = new TeaNameHandler();
    private final OrderAttributeHandler<Type> typeHandler = new TeaTypeHandler();
    private final OrderAttributeHandler<Color> colorHandler = new ColorHandler();

    @Test
    void itShouldReturnAdditionAwaitingState() {
        final TeaName teaName = TestUtils.getRandomAttribute(TeaName.values());
        final OrderInfo order = nameHandler.updateOrder(new OrderInfo(), teaName);

        assertEquals(teaName.getValue(), order.getTea().getName());
    }

    @Test
    void itShouldRemoveTypeAndColorWhenNameSaved() {
        final Type type = TestUtils.getRandomAttribute(Type.values());
        final Color color = TestUtils.getRandomAttribute(Color.values());
        final TeaName teaName = TestUtils.getRandomAttribute(TeaName.values());

        OrderInfo order = typeHandler.updateOrder(new OrderInfo(), type);
        order = colorHandler.updateOrder(order, color);

        final Tea tea = order.getTea();
        assertEquals(type, tea.getType());
        assertEquals(color, tea.getColor());

        order = nameHandler.updateOrder(order, teaName);

        assertEquals(teaName.getValue(), tea.getName());
        assertNull(tea.getColor());
        assertNull(tea.getType());
    }
}