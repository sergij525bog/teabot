package com.example.teabot.model.handlers;

import com.example.teabot.model.orderInfo.OrderInfo;
import com.example.teabot.model.enums.OrderState;
import com.example.teabot.utils.StringUtil;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

class StartHandler implements OrderAttributeHandler {

    @Override
    public ReplyKeyboard getMarkup() {
        return null;
    }

    @Override
    public String question() {
        return "You want to create tea or ask someone to do it?";
    }

    @Override
    public OrderState processUserInput(String data, OrderInfo orderInfo) {
        if (StringUtil.isStartCommand(data)) {
            return OrderState.TEA_MAKER_BUILDING_PROPOSAL;
        }

        return OrderState.ERROR;
    }
}
