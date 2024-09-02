package com.example.teabot.handlers.state;

import com.example.teabot.handlers.OrderStateHandler;
import com.example.teabot.model.enums.OrderState;
import com.example.teabot.model.enums.cup.CupBuildingType;
import com.example.teabot.model.orderInfo.OrderInfo;
import com.example.teabot.utils.TestUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CupBuildingTypeStateHandlerTest {

    public static final OrderState INITIAL_STATE = OrderState.CUP_BUILDING_TYPE_PROPOSAL;
    private final OrderStateHandler<CupBuildingType> handler = new CupBuildingTypeStateHandler();

    @Test
    void itShouldSaveCupNameAwaitingState() {
        OrderInfo order = TestUtils.getOrder(INITIAL_STATE);

        order = handler.updateOrderState(order, CupBuildingType.BY_NAME);

        assertEquals(OrderState.CUP_NAME_AWAITING, order.getCurrentState());
        assertEquals(INITIAL_STATE, order.getPrevState());
    }

    @Test
    void itShouldSaveCupSizeAwaitingState() {
        OrderInfo order = TestUtils.getOrder(INITIAL_STATE);

        order = handler.updateOrderState(order, CupBuildingType.BY_SIZE);

        assertEquals(OrderState.CUP_SIZE_AWAITING, order.getCurrentState());
        assertEquals(INITIAL_STATE, order.getPrevState());
    }
}