package com.example.teabot.handlers.state;

import com.example.teabot.handlers.OrderStateHandler;
import com.example.teabot.model.enums.NavigationButtons;
import com.example.teabot.model.enums.OrderState;
import com.example.teabot.model.enums.tea.Additive;
import com.example.teabot.model.orderInfo.OrderInfo;

import java.util.EnumMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import static com.example.teabot.model.enums.OrderState.CUP_BUILDING_TYPE_PROPOSAL;
import static com.example.teabot.model.enums.OrderState.ERROR;

class NavigationHandler implements OrderStateHandler<NavigationButtons> {

    private static final Map<NavigationButtons, Function<OrderInfo, OrderState>>
            STATE_NAVIGATORS = initializeMap();

    @Override
    public OrderInfo updateOrderState(OrderInfo order, NavigationButtons param) {
        final OrderState state = STATE_NAVIGATORS.get(param).apply(order);

        order.setCurrentState(state);

        return order;
    }

    private static OrderState skipOrder(OrderInfo order) {
        order.setDefaults();
        return OrderState.SAVE_ORDER_AWAITING;
    }

    private static OrderState getPrevState(OrderInfo order) {
        final OrderState currentState = order.getCurrentState();

        if (currentState != ERROR) {
            return order.getPrevState();
        }

        return ERROR;
    }

    private static OrderState getNextState(OrderInfo order) {
        final OrderState currentState = order.getCurrentState();

        final Set<Additive> additives = order.getTea().getAdditives();
        if (currentState == OrderState.ADDITIONS_AWAITING && !additives.isEmpty()) {
            order.setNextState(CUP_BUILDING_TYPE_PROPOSAL);
        }

        return order.getNextState();
    }

    private static Map<NavigationButtons, Function<OrderInfo, OrderState>> initializeMap() {
        final Map<NavigationButtons, Function<OrderInfo, OrderState>> map = new EnumMap<>(NavigationButtons.class);

        map.put(NavigationButtons.NEXT, NavigationHandler::getNextState);
        map.put(NavigationButtons.BACK, NavigationHandler::getPrevState);
        map.put(NavigationButtons.SKIP, NavigationHandler::skipOrder);
        map.put(NavigationButtons.CANCEL, order -> OrderState.CANCEL_ORDER);

        return map;
    }
}
