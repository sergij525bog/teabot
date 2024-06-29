package com.example.teabot.model.handlers;

import com.example.teabot.model.orderInfo.OrderInfo;
import com.example.teabot.model.enums.NavigationButtons;
import com.example.teabot.model.enums.OrderState;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

class NavigationHandler implements OrderAttributeHandler {
    @Override
    public String question() {
        return null;
    }

    @Override
    public OrderState processUserInput(String data, OrderInfo orderInfo) {
        NavigationButtons instance = NavigationButtons.getInstance(data);

        return switch (instance) {
            case NEXT -> orderInfo.getNextState();
            case BACK -> orderInfo.getPrevState();
            case SKIP -> {
                orderInfo.setDefaults();
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
