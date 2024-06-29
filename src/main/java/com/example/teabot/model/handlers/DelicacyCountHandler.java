package com.example.teabot.model.handlers;

import com.example.teabot.model.orderInfo.Delicacy;
import com.example.teabot.model.orderInfo.OrderInfo;
import com.example.teabot.model.enums.OrderParameter;
import com.example.teabot.model.enums.OrderState;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

import java.util.stream.IntStream;

class DelicacyCountHandler implements OrderAttributeHandler {

    @Override
    public String question() {
        return "Input delicacy count";
    }

    @Override
    public OrderState processUserInput(String data, OrderInfo orderInfo) {
        //        todo: add string validation
        Delicacy delicacy = orderInfo.getDelicacy();
        delicacy.setCount(Byte.parseByte(data));

        return OrderState.SAVE_ORDER_AWAITING;
    }

    @Override
    public ReplyKeyboard getMarkup() {
        OrderParameter orderParameter = () -> IntStream.iterate(1, i -> i + 1)
                .limit(5)
                .mapToObj(String::valueOf);
        return KeyboardFactory.getKeyboardByParameter(orderParameter, question());
    }
}
