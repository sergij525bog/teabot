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
    public OrderState updateOrder(OrderInfo order, String orderAttribute) {
        final NavigationButtons instance = NavigationButtons.getInstance(orderAttribute);

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
