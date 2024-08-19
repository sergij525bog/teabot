package com.example.teabot.handlers;

import com.example.teabot.model.enums.NavigationButtons;
import com.example.teabot.model.enums.OrderState;
import com.example.teabot.model.orderInfo.OrderInfo;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

class NavigationHandler implements OrderAttributeHandler {
    @Override
    public String question() {
        return null;
    }

    @Override
    public OrderInfo updateOrder(OrderInfo order, String orderAttribute) {
        final NavigationButtons instance = NavigationButtons.getInstance(orderAttribute);

        final OrderState state = calculateNewState(order, instance);
        order.setCurrentState(state);

        return order;
    }

    private static OrderState calculateNewState(OrderInfo order, NavigationButtons instance) {
        return switch (instance) {
            case NEXT -> order.getNextState();
            case BACK -> order.getPrevState();
            case SKIP -> {
                order.setDefaults();
                yield OrderState.SAVE_ORDER_AWAITING;
            }
            case CANCEL -> OrderState.CANCEL_ORDER;
        };
    }

    @Override
    public ReplyKeyboard getMarkup() {
        return null;
    }
}
