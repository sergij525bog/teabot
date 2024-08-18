package com.example.teabot.handlers;

import com.example.teabot.model.enums.OrderState;
import com.example.teabot.model.enums.delicacy.DelicacyType;
import com.example.teabot.model.orderInfo.Delicacy;
import com.example.teabot.model.orderInfo.OrderInfo;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

class DelicacyTypeHandler implements OrderAttributeHandler {

    @Override
    public String question() {
        return "Input delicacy type";
    }

    @Override
    public OrderState updateOrder(OrderInfo order, String orderAttribute) {
//        todo: add string validation
        final Delicacy delicacy = order.getDelicacy();
        delicacy.setType(orderAttribute);

        if (DelicacyType.NONE.getType().equals(orderAttribute)) {
            return OrderState.SAVE_ORDER_AWAITING;
        }

        return OrderState.DELICACY_COUNT_AWAITING;
    }

    @Override
    public ReplyKeyboard getMarkup() {
        return KeyboardFactory.getKeyboardByParameter(DelicacyType.CAKE, question());
    }
}
