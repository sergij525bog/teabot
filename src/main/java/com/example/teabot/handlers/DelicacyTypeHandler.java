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
    public OrderInfo updateOrder(OrderInfo order, String orderAttribute) {
//        todo: add string validation
        final Delicacy delicacy = order.getDelicacy();
        delicacy.setType(orderAttribute);

        final OrderState state = DelicacyType.NONE.getType().equals(orderAttribute)
                ? OrderState.SAVE_ORDER_AWAITING
                : OrderState.DELICACY_COUNT_AWAITING;
        order.setCurrentState(state);

        return order;
    }

    @Override
    public ReplyKeyboard getMarkup() {
        return KeyboardFactory.getKeyboardByParameter(DelicacyType.CAKE, question());
    }
}
