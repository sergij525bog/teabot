package com.example.teabot.model.views;

import com.example.teabot.TeaOrderHandler;
import com.example.teabot.model.Tea;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

public class StopView extends AbstractTeaButtonView implements TeaButtonView {
    private TeaOrderHandler.ChatInfo orderInfo;
    public StopView(Tea tea) {
        super(tea);
    }

    public StopView(Tea tea, TeaOrderHandler.ChatInfo orderInfo) {
        super(tea);
        this.orderInfo = orderInfo;
    }

    @Override
    public String question() {
        return "Order saved:\n" + orderInfo.toString() + "\nPlease print '/start' to create new order";
    }

    @Override
    public void updateTea(Tea tea, Update update) {
    }

    @Override
    public ReplyKeyboard getMarkup() {
        return null;
    }
}
