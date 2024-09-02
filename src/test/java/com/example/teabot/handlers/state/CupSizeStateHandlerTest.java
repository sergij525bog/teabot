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

class CupSizeStateHandlerTest {

    private static final OrderState INITIAL_SIZE_STATE = OrderState.CUP_SIZE_AWAITING;
    private static final OrderState NEXT_STATE = OrderState.DELICACY_TYPE_AWAITING;
    private static final OrderState INITIAL_NAME_STATE = OrderState.CUP_NAME_AWAITING;

    private final OrderStateHandler<CupSize> sizeHandler = new CupSizeStateHandler();
    private final OrderStateHandler<CupName> nameHandler = new CupNameStateHandler();

    @Test
    void itShouldSaveNewState() {
        OrderInfo order = TestUtils.getOrder(INITIAL_SIZE_STATE);

        final CupSize size = TestUtils.getRandomAttribute(CupSize.values());

        order = sizeHandler.updateOrderState(order, size);

        assertEquals(NEXT_STATE, order.getCurrentState());
        assertEquals(INITIAL_SIZE_STATE, order.getPrevState());
    }

    @Test
    void ItShouldRewriteState() {
        OrderInfo order = TestUtils.getOrder(INITIAL_NAME_STATE);

        final CupName name = TestUtils.getRandomAttribute(CupName.values());
        final CupSize size = TestUtils.getRandomAttribute(CupSize.values());

        order = nameHandler.updateOrderState(order, name);

        assertEquals(NEXT_STATE, order.getCurrentState());
        assertEquals(INITIAL_NAME_STATE, order.getPrevState());

        order = switchToCupSizeBranch(order);

        assertEquals(INITIAL_SIZE_STATE, order.getCurrentState());

        order = sizeHandler.updateOrderState(order, size);

        assertEquals(NEXT_STATE, order.getCurrentState());
        assertEquals(INITIAL_SIZE_STATE, order.getPrevState());
    }

    private OrderInfo switchToCupSizeBranch(OrderInfo order) {
        NavigationHandler navigationHandler = new NavigationHandler();

        order = navigationHandler.updateOrderState(order, NavigationButtons.BACK);
        order = navigationHandler.updateOrderState(order, NavigationButtons.BACK);

        return StateHandlerFactory
                .getHandlerByState(order.getCurrentState())
                .updateOrderState(order, CupBuildingType.BY_SIZE);
    }
}