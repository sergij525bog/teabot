package com.example.teabot.handlers.attribute;

import com.example.teabot.handlers.OrderAttributeHandler;
import com.example.teabot.model.enums.OrderState;
import com.example.teabot.model.enums.tea.TeaName;
import com.example.teabot.model.orderInfo.OrderInfo;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class TeaNameHandlerTest {

    private final OrderAttributeHandler handler = new TeaNameHandler();

    @Test
    void isShouldReturnErrorStateIsDataIsIncorrect() {
        final OrderInfo order = handler.updateOrder(new OrderInfo(), "");

        assertEquals(OrderState.ERROR, order.getCurrentState());
    }

    @Test
    void itShouldReturnAdditionAwaitingState() {
        final TeaName[] teaNames = TeaName.values();
        final int index = new Random().nextInt(0, teaNames.length);
        final OrderInfo order1 = handler.updateOrder(new OrderInfo(), teaNames[index].getValue());

        final OrderInfo order2 = handler.updateOrder(new OrderInfo(), "some tea name");

        assertEquals(OrderState.ADDITIONS_AWAITING, order1.getCurrentState());
        assertEquals(OrderState.ADDITIONS_AWAITING, order2.getCurrentState());
    }
}