package com.example.teabot.handlers.attribute;

import com.example.teabot.handlers.OrderAttributeHandler;
import com.example.teabot.model.enums.tea.TeaName;
import com.example.teabot.model.enums.tea.Type;
import com.example.teabot.model.orderInfo.OrderInfo;
import com.example.teabot.utils.TestUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class TeaTypeHandlerTest {

    private final OrderAttributeHandler<TeaName> nameHandler = new TeaNameHandler();
    private final OrderAttributeHandler<Type> typeHandler = new TeaTypeHandler();

    @Test
    void itShouldSaveTeaType() {
        final Type type = TestUtils.getRandomAttribute(Type.values());

        final OrderInfo order = typeHandler.updateOrder(new OrderInfo(), type);

        assertEquals(type, order.getTea().getType());
    }

    @Test
    void itShouldRemoveTeaNameWhenTypeSaved() {
        final Type type = TestUtils.getRandomAttribute(Type.values());
        final TeaName teaName = TestUtils.getRandomAttribute(TeaName.values());

        OrderInfo order = nameHandler.updateOrder(new OrderInfo(), teaName);

        assertEquals(teaName.getValue(), order.getTea().getName());

        order = typeHandler.updateOrder(new OrderInfo(), type);

        assertEquals(type, order.getTea().getType());
        assertNull(order.getTea().getName());
    }

}