package com.example.teabot.model.handlers;

import com.example.teabot.model.ChatInfo;
import com.example.teabot.model.enums.AttributeUpdateStatus;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

@NoArgsConstructor
@AllArgsConstructor
@Setter
public class OrderSavingHandler implements AttributeHandler {
    private ChatInfo orderInfo;

    @Override
    public String question() {
        return "Order saved:\n" + orderInfo.toString() + "\nPlease print '/start' to create new order";
    }

    @Override
    public AttributeUpdateStatus updateAttribute(String data, ChatInfo orderInfo) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ReplyKeyboard getMarkup() {
        return null;
    }
}
