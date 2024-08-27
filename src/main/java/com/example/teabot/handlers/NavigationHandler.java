package com.example.teabot.handlers;

import com.example.teabot.model.enums.NavigationButtons;
import com.example.teabot.model.enums.OrderState;
import com.example.teabot.model.enums.tea.Additive;
import com.example.teabot.model.orderInfo.OrderInfo;

import java.util.Set;

import static com.example.teabot.model.enums.OrderState.CUP_BUILDING_TYPE_PROPOSAL;
import static com.example.teabot.model.enums.OrderState.ERROR;

public class NavigationHandler {

    public OrderInfo updateOrder(OrderInfo order, String attributeAsString) {
        final NavigationButtons instance = NavigationButtons.getInstance(attributeAsString);
        final OrderState state = calculateNewState(order, instance);

        order.setCurrentState(state);

        return order;
    }

    private static OrderState calculateNewState(OrderInfo order, NavigationButtons instance) {
        return switch (instance) {
            case NEXT -> {
                final OrderState currentState = order.getCurrentState();
//
                final Set<Additive> additives = order.getTea().getAdditives();
                if (currentState == OrderState.ADDITIONS_AWAITING && !additives.isEmpty()) {
                    order.setNextState(CUP_BUILDING_TYPE_PROPOSAL);
                }

                yield order.getNextState();
            }
            case BACK -> {
                final OrderState currentState = order.getCurrentState();

                if (currentState != ERROR) {
                    yield order.getPrevState();
                }

                yield ERROR;
            }
            case SKIP -> {
                order.setDefaults();
                yield OrderState.SAVE_ORDER_AWAITING;
            }
            case CANCEL -> OrderState.CANCEL_ORDER;
        };
    }
}
