package com.example.teabot.handlers.state;

import com.example.teabot.handlers.OrderStateHandler;
import com.example.teabot.model.enums.OrderState;
import com.example.teabot.model.enums.StartCommand;
import com.example.teabot.model.orderInfo.OrderInfo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StartStateHandlerTest {

    private static final OrderState NEXT_STATE = OrderState.TEA_MAKER_BUILDING_PROPOSAL;
    private final OrderStateHandler<StartCommand> handler = new StartStateHandler();

    @Test
    void itShouldSaveNewState() {
        final OrderInfo order1 = handler.updateOrderState(new OrderInfo(), StartCommand.START);
        final OrderInfo order2 = handler.updateOrderState(new OrderInfo(), StartCommand.START_TEA_BOT);

        assertEquals(NEXT_STATE, order1.getCurrentState());
        assertEquals(NEXT_STATE, order2.getCurrentState());
    }

}