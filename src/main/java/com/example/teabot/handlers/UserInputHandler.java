package com.example.teabot.handlers;

import com.example.teabot.handlers.attribute.AttributeHandlerFactory;
import com.example.teabot.handlers.state.StateHandlerFactory;
import com.example.teabot.model.enums.NavigationButtons;
import com.example.teabot.model.enums.OrderAttribute;
import com.example.teabot.model.enums.OrderState;
import com.example.teabot.model.orderInfo.OrderInfo;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.example.teabot.model.enums.OrderState.ERROR;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserInputHandler {

    public static OrderInfo handle(OrderInfo order, String input) {
        if (NavigationButtons.isNavigation(input)) {
            return AttributeHandlerFactory
                    .getNavigationHandler()
                    .updateOrder(order, input);
        }

        final OrderState currentState = order.getCurrentState();
        final OrderState state = currentState != ERROR
                ? currentState
                : order.getPrevState();

        return AttributeResolver.resolve(state, input)
                .map(attribute -> doUpdate(order, state, attribute))
                .orElseGet(() -> updateOrderWithError(order));
    }

    private static <T extends OrderAttribute> OrderInfo doUpdate(OrderInfo order, OrderState state, T attribute) {
        final var attributeHandler = AttributeHandlerFactory.getHandlerByState(state);
        final var stateHandler = StateHandlerFactory.getHandlerByState(state);

        final OrderInfo orderInfo = attributeHandler.updateOrder(order, attribute);
        return stateHandler.updateOrderState(orderInfo, attribute);
    }

    private static OrderInfo updateOrderWithError(OrderInfo order) {
        final OrderState state = order.getCurrentState();
        order.setCurrentState(OrderState.ERROR);
        if (state != ERROR) {
            order.setPrevState(state);
        }
        return order;
    }
}
