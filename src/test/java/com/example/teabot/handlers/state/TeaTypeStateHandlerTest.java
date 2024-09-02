package com.example.teabot.handlers.state;

import com.example.teabot.handlers.OrderStateHandler;
import com.example.teabot.model.enums.NavigationButtons;
import com.example.teabot.model.enums.OrderState;
import com.example.teabot.model.enums.tea.TeaBuildingType;
import com.example.teabot.model.enums.tea.TeaName;
import com.example.teabot.model.enums.tea.Type;
import com.example.teabot.model.orderInfo.OrderInfo;
import com.example.teabot.utils.TestUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TeaTypeStateHandlerTest {

    private static final OrderState INITIAL_TYPE_STATE = OrderState.TYPE_SELECTION_AWAITING;
    private static final OrderState NEXT_STATE = OrderState.COLOR_SELECTION_AWAITING;
    private static final OrderState INITIAL_NAME_STATE = OrderState.INPUT_NAME_AWAITING;

    private final OrderStateHandler<Type> handler = new TeaTypeStateHandler();
    private final OrderStateHandler<TeaName> nameHandler = new TeaNameStateHandler();

    @Test
    void itShouldSaveNewState() {
        OrderInfo order = TestUtils.getOrder(INITIAL_TYPE_STATE);

        final Type type = TestUtils.getRandomAttribute(Type.values());

        order = handler.updateOrderState(order, type);

        assertEquals(NEXT_STATE, order.getCurrentState());
        assertEquals(INITIAL_TYPE_STATE, order.getPrevState());
    }

    @Test
    void itShouldRewriteState() {
        OrderInfo order = TestUtils.getOrder(INITIAL_NAME_STATE);

        final TeaName teaName = TestUtils.getRandomAttribute(TeaName.values());
        final Type type = TestUtils.getRandomAttribute(Type.values());

        order = nameHandler.updateOrderState(order, teaName);

        assertEquals(OrderState.ADDITIONS_AWAITING, order.getCurrentState());
        assertEquals(INITIAL_NAME_STATE, order.getPrevState());

        order = switchToTeaDescriptionBranch(order);

        assertEquals(INITIAL_TYPE_STATE, order.getCurrentState());

        order = handler.updateOrderState(order, type);

        assertEquals(NEXT_STATE, order.getCurrentState());
        assertEquals(INITIAL_TYPE_STATE, order.getPrevState());
    }

    private OrderInfo switchToTeaDescriptionBranch(OrderInfo order) {
        NavigationHandler navigationHandler = new NavigationHandler();

        order = navigationHandler.updateOrderState(order, NavigationButtons.BACK);
        order = navigationHandler.updateOrderState(order, NavigationButtons.BACK);
        
        return StateHandlerFactory
                .getHandlerByState(order.getCurrentState())
                .updateOrderState(order, TeaBuildingType.BY_DESCRIPTION);
    }
}