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

class TeaNameStateHandlerTest {

    private static final OrderState INITIAL_NAME_STATE = OrderState.INPUT_NAME_AWAITING;
    private static final OrderState NEXT_STATE = OrderState.ADDITIONS_AWAITING;
    private static final OrderState INITIAL_TYPE_STATE = OrderState.TYPE_SELECTION_AWAITING;

    private final OrderStateHandler<TeaName> nameHandler = new TeaNameStateHandler();
    private final OrderStateHandler<Type> typeHandler = new TeaTypeStateHandler();

    @Test
    void itShouldSaveNewState() {
        OrderInfo order = getOrderInfo(INITIAL_NAME_STATE);

        final TeaName teaName = TestUtils.getRandomAttribute(TeaName.values());

        order = nameHandler.updateOrderState(order, teaName);

        assertEquals(NEXT_STATE, order.getCurrentState());
        assertEquals(INITIAL_NAME_STATE, order.getPrevState());
    }

    @Test
    void itShouldRewriteState() {
        OrderInfo order = getOrderInfo(INITIAL_TYPE_STATE);

        final TeaName teaName = TestUtils.getRandomAttribute(TeaName.values());
        final Type type = TestUtils.getRandomAttribute(Type.values());

        order = typeHandler.updateOrderState(order, type);

        assertEquals(OrderState.COLOR_SELECTION_AWAITING, order.getCurrentState());
        assertEquals(INITIAL_TYPE_STATE, order.getPrevState());

        order = switchToTeaNameSelectionBranch(order);

        assertEquals(INITIAL_NAME_STATE, order.getCurrentState());

        order = nameHandler.updateOrderState(order, teaName);

        assertEquals(NEXT_STATE, order.getCurrentState());
        assertEquals(INITIAL_NAME_STATE, order.getPrevState());
    }

    private OrderInfo switchToTeaNameSelectionBranch(OrderInfo order) {
        NavigationHandler navigationHandler = new NavigationHandler();

        order = navigationHandler.updateOrderState(order, NavigationButtons.BACK);
        order = navigationHandler.updateOrderState(order, NavigationButtons.BACK);
        
        return StateHandlerFactory
                .getHandlerByState(order.getCurrentState())
                .updateOrderState(order, TeaBuildingType.BY_NAME);
    }

    private static OrderInfo getOrderInfo(OrderState state) {
        OrderInfo order = new OrderInfo();
        order.setCurrentState(state);
        return order;
    }
}