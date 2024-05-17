package com.example.teabot.model.handlers;

import com.example.teabot.model.ChatInfo;
import com.example.teabot.model.enums.OrderState;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;

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
    public OrderState processUserInput(String data, ChatInfo orderInfo) {
        return null;
    }

    @Override
    public ReplyKeyboard getMarkup() {
        return ReplyKeyboardRemove.builder()
                .removeKeyboard(true)
                .selective(true)
                .build();
    }
}
