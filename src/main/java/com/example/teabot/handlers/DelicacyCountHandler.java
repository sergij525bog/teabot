package com.example.teabot.handlers;

import com.example.teabot.model.enums.OrderParameter;
import com.example.teabot.model.enums.OrderState;
import com.example.teabot.model.orderInfo.Delicacy;
import com.example.teabot.model.orderInfo.OrderInfo;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

import java.util.stream.IntStream;

class DelicacyCountHandler implements OrderAttributeHandler {

    @Override
    public String question() {
        return "Input delicacy count";
    }

    @Override
    public OrderInfo updateOrder(OrderInfo order, String orderAttribute) {
        //        todo: add string validation
        final Delicacy delicacy = order.getDelicacy();
        final byte count = Byte.parseByte(orderAttribute);

        if (count < Delicacy.MIN || count > Delicacy.MAX) {
            return updateOrderWithError(order);
        }

        delicacy.setCount(count);
        order.setCurrentState(OrderState.SAVE_ORDER_AWAITING);

        return order;
    }

    @Override
    public ReplyKeyboard getMarkup() {
        final OrderParameter orderParameter = () -> IntStream.iterate(1, i -> i + 1)
                .limit(5)
                .mapToObj(String::valueOf);
        return KeyboardFactory.getKeyboardByParameter(orderParameter, question());
    }
}
