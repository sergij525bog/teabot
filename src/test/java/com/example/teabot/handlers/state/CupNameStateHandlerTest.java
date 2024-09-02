package com.example.teabot.handlers.state;

import com.example.teabot.handlers.OrderStateHandler;
import com.example.teabot.model.enums.NavigationButtons;
import com.example.teabot.model.enums.OrderState;
import com.example.teabot.model.enums.cup.CupBuildingType;
import com.example.teabot.model.enums.cup.CupName;
import com.example.teabot.model.enums.cup.CupSize;
import com.example.teabot.model.orderInfo.OrderInfo;
import com.example.teabot.utils.TestUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CupNameStateHandlerTest {

    private static final OrderState INITIAL_NAME_STATE = OrderState.CUP_NAME_AWAITING;
    private static final OrderState NEXT_STATE = OrderState.DELICACY_TYPE_AWAITING;
    private static final OrderState INITIAL_SIZE_STATE = OrderState.CUP_SIZE_AWAITING;

    private final OrderStateHandler<CupName> handler = new CupNameStateHandler();
    private final OrderStateHandler<CupSize> sizeHandler = new CupSizeStateHandler();

    @Test
    void itShouldSaveNewState() {
        OrderInfo order = TestUtils.getOrder(INITIAL_NAME_STATE);

        final CupName cupName = TestUtils.getRandomAttribute(CupName.values());

        order = handler.updateOrderState(order, cupName);

        assertEquals(NEXT_STATE, order.getCurrentState());
        assertEquals(INITIAL_NAME_STATE, order.getPrevState());
    }

    @Test
    void itShouldRewritePrevState() {
        OrderInfo order = TestUtils.getOrder(INITIAL_SIZE_STATE);

        final CupName cupName = TestUtils.getRandomAttribute(CupName.values());
        final CupSize cupSize = TestUtils.getRandomAttribute(CupSize.values());

        order = sizeHandler.updateOrderState(order, cupSize);

        assertEquals(NEXT_STATE, order.getCurrentState());
        assertEquals(INITIAL_SIZE_STATE, order.getPrevState());

        order = switchToCupNameBranch(order);

        assertEquals(INITIAL_NAME_STATE, order.getCurrentState());

        order = handler.updateOrderState(order, cupName);

        assertEquals(NEXT_STATE, order.getCurrentState());
        assertEquals(INITIAL_NAME_STATE, order.getPrevState());
    }

    private OrderInfo switchToCupNameBranch(OrderInfo order) {
        NavigationHandler navigationHandler = new NavigationHandler();

        order = navigationHandler.updateOrderState(order, NavigationButtons.BACK);
        order = navigationHandler.updateOrderState(order, NavigationButtons.BACK);

        return StateHandlerFactory
                .getHandlerByState(order.getCurrentState())
                .updateOrderState(order, CupBuildingType.BY_NAME);
    }
}